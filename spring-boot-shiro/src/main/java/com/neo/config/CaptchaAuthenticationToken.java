package com.neo.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * spring-boot-shiro-CaptchaAuthenticationToken
 * 扩展shiro认证接口，加入对验证码的读取
 *
 * @author yh
 * @since 0.0.1 2017-09-03 22:47
 */
@Data
@NoArgsConstructor
public class CaptchaAuthenticationToken extends UsernamePasswordToken {

    private String captcha;

    public CaptchaAuthenticationToken(String username, String password,
                                      boolean rememberMe, String host, String captcha) {
        super(username, password, rememberMe, host);
        this.captcha = captcha;
    }
}
