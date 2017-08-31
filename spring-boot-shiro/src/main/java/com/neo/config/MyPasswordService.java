package com.neo.config;

import com.neo.common.CryptoUtil;
import com.neo.sevice.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * spring-boot-shiro-MyPasswordService
 * 实现pbkdf2的password service
 *
 * @author yh
 * @since 0.0.1 2017-08-30 21:48
 */

@Slf4j
@Service
public class MyPasswordService  implements PasswordService {

    //todo 盐值线程不安全的？怎么去改，盐值如何保存？这里建个map保存,但是这个接口根本不知道username是谁？<username - salt>
    private byte[] salt;

    @Autowired
    UserInfoService userInfoService;

    @Override
    public String encryptPassword(Object plaintextPassword) throws IllegalArgumentException {
        log.debug("encrypt password with pbkdf2.");
        String encryptPassword = "";
        return encryptPassword;
    }

    // public String encrytPassword(String username, String plaintextPassword){
    //     log.debug("encrypt password with pbkdf2.");
    //     if (Strings.isNullOrEmpty(username)){
    //         log.error("the username is null.");
    //     }
    //     UserInfo user = userInfoService.findByUsername(username);
    //     this.salt = user.getSalt().getBytes();
    //     return encryptPassword(plaintextPassword);
    // }

    @Override
    public boolean passwordsMatch(Object submittedPlaintext, String encrypted) {
        log.debug("verity the password submitted.");

        boolean result = false;
        try {
            result = CryptoUtil.validatePassword(new String((char[]) submittedPlaintext), encrypted);
        } catch (DecoderException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("validate password failed, caused by: {}", e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}
