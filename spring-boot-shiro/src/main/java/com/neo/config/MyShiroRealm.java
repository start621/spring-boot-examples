package com.neo.config;

import com.neo.entity.Permission;
import com.neo.entity.Role;
import com.neo.entity.UserInfo;
import com.neo.entity.UserStatus;
import com.neo.sevice.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

@Slf4j
public class MyShiroRealm extends AuthorizingRealm {
    @Resource
    private UserInfoService userInfoService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserInfo userInfo  = (UserInfo)principals.getPrimaryPrincipal();
        for (Role role : userInfo.getRoleList()) {
            authorizationInfo.addRole(role.getName());
            for (Permission p : role.getPermissions()) {
                for (String privilege : p.getPrivileges()) {
                    authorizationInfo.addStringPermission(privilege);
                }
            }
        }
        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        log.info("start verify the user identity in realm: {}.", this.getClass().getSimpleName());
        //前台出入参数一般为用户名和密码，将AuthenticationToken转换为UsernamePasswordToken
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        //获取用户的输入的账号.
        //String username = (String)token.getPrincipal();
        String username = usernamePasswordToken.getUsername();
        if(username == null || username.isEmpty())
        {
            log.error("the input is empty!");
            throw new UnknownAccountException("the input is empty");
        }
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        UserInfo userInfo = userInfoService.findByUsername(username);
        //根据用户信息判定登录情况

        if(userInfo == null){
            log.error("user: [{}] not exist.", username);
            throw new UnknownAccountException("user not exist.");
        }
        if (userInfo.getState().equals(UserStatus.LOCKED))
        {
            log.error("user: [{}] is locked now.", username);
            throw new LockedAccountException("user is locked now.");
        }
        // SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
        //         userInfo, //用户名
        //         userInfo.getPassword(), //密码
        //         // ByteSource.Util.bytes(userInfo.getCredentialsSalt()),//salt=username+salt
        //         getName()  //realm name
        // );
        return new SimpleAuthenticationInfo(userInfo, userInfo.getPassword(), getName());
    }

}