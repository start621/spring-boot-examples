package com.neo.config;

import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * spring-boot-shiro-ShiroConfig
 * 通过注解的方式完成spring的配置,配置shiro security manager
 *
 * @author yh
 * @since 0.0.1 2017-08-25 22:14
 */

@Configuration
public class ShiroConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        System.out.println("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("authc", new CaptchaFormAuthenticationFilter());

        //拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置不会被拦截的链接 顺序判断
        // filterChainDefinitionMap.put("/userManagement/**", "anon");
        //todo 1.对于权限的配置增加缓存 2.可以增加动态配置功能，从数据库中灵活读取配置，完全通过perms过滤器实现对资源的rest接口的控制，可以通过roles过滤器实现对页面的一些控制
        filterChainDefinitionMap.put("/userInfoDel", "roles[admin]");
        filterChainDefinitionMap.put("/userInfo", "perms[users:read]");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/captcha", "anon");
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc");
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");

        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilters(filterMap);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * ）
     * @return 返回对应的密码校验器
     */
    @Bean
    public PasswordMatcher myPasswordMatcher(){
        PasswordMatcher passwordMatcher = new PasswordMatcher();
        passwordMatcher.setPasswordService(myPasswordService());
        return passwordMatcher;
    }
    // @Bean
    // public HashedCredentialsMatcher hashedCredentialsMatcher(){
    // 	HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
    // 	hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
    // 	hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
    // 	return hashedCredentialsMatcher;
    // }


    private PasswordService myPasswordService() {
        return new MyPasswordService();
    }


    @Bean
    public MyShiroRealm myShiroRealm(){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(myPasswordMatcher());
        return myShiroRealm;
    }

    // @Bean
    // public CaptchaFormAuthenticationFilter myAuthcFilter() {
    // 	return  new CaptchaFormAuthenticationFilter();
    // }


    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        securityManager.setCacheManager(cacheManager()); //ehcache缓存可以加入
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager securityManager
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean(name="simpleMappingExceptionResolver")
    public SimpleMappingExceptionResolver
    createSimpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
        Properties mappings = new Properties();
        mappings.setProperty("DatabaseException", "databaseError");//数据库异常处理
        mappings.setProperty("UnauthorizedException","403");
        r.setExceptionMappings(mappings);  // None by default
        r.setDefaultErrorView("error");    // No default
        r.setExceptionAttribute("ex");     // Default is "exception"
        //r.setWarnLogCategory("example.MvcLogger");     // No default
        return r;
    }

    /**
     * 初始化shiro-redis的redis配置，以便cacheManager使用
     *
     * @return RedisManager
     */
    private RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        // redisManager.setExpire(600);
        return redisManager;
    }

    /**
     * 利用shiro-redis 设置shiro的cacheManager
     *
     * @return RedisCacheManager
     */
    //todo 是否先设置ehcache本地缓存，而不是直接去redis获取
    private RedisCacheManager cacheManager() {
        RedisCacheManager cacheManager = new RedisCacheManager();
        cacheManager.setRedisManager(redisManager());
        return cacheManager;
    }

    /**
     * 利用shiro-redis 设置对应的session缓存，该类中重写了对session的crud方法
     *
     * @return redisSessionDAO
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO sessionDAO = new RedisSessionDAO();
        sessionDAO.setRedisManager(redisManager());
        return sessionDAO;
    }

    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }

}