package com.neo.config;

import com.google.common.base.Strings;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * spring-boot-shiro-CaptchaFormAuthenticationFilter
 * 扩展shiro的校验功能，加入对验证码的校验
 *
 * @author yh
 * @since 0.0.1 2017-09-03 22:50
 */
@Slf4j
@Getter
public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter {

    private static final String CAPTCHA_SESSION_KEY = "verifyCode";
    // private String captchaParam = "captcha";

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, CAPTCHA_SESSION_KEY);
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request,
                                              ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        String captcha = getCaptcha(request);
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        return new CaptchaAuthenticationToken(username, password, rememberMe,
                host, captcha);
    }

    // 验证码校验
    protected void doCaptchaValidate(HttpServletRequest request, CaptchaAuthenticationToken token) {
        String captcha = (String) request.getSession().getAttribute(CAPTCHA_SESSION_KEY);

        String parameter = request.getParameter(CAPTCHA_SESSION_KEY);
        log.debug("session verify code " + token.getCaptcha() + "from verify code " + parameter);

        if (Strings.isNullOrEmpty(token.getCaptcha()) || !token.getCaptcha().equalsIgnoreCase(captcha)) {
            /* 定义IncorrectCaptchaException，shiro显示Exception class name作为error信息 */
            throw new AuthenticationException("验证码错误！");
        }
    }

    /*
      protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
        String className = ae.getClass().getName();
        request.setAttribute(getFailureKeyAttribute(), className);
    }
    */
    // 认证
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        CaptchaAuthenticationToken token = (CaptchaAuthenticationToken) createToken(request, response);

        try {
            doCaptchaValidate((HttpServletRequest) request, token);

            Subject subject = getSubject(request, response);
            subject.login(token);

            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
            return onLoginFailure(token, e, request, response);
        }
    }
}
