package com.neo.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.ByteSource;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * spring-boot-shiro-CryptoUtil
 * 加解密工具类
 *
 * @author yh
 * @since 0.0.1 2017-08-30 21:26
 */
@Slf4j
public class CryptoUtil {

    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int ITERATION = 50000;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH =8;

    public static byte[] generateSalt() {
        log.debug("start generate the salt");

        byte[] salt = new byte[SALT_LENGTH];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);

        return salt;
    }

    public static String pbkdf2Encrypt(String plainText, byte[] salt) throws
            NoSuchAlgorithmException, InvalidKeySpecException {
        String hashedText;
        ByteSource byteSourceSalt = ByteSource.Util.bytes(salt);
        // 获取密钥生成工厂类
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
        // 转换密钥材料
        KeySpec keySpec = new PBEKeySpec(plainText.toCharArray(), byteSourceSalt.getBytes(), ITERATION, KEY_LENGTH);
        SecretKey secretKey = factory.generateSecret(keySpec);
        Key key = new SecretKeySpec(secretKey.getEncoded(), "AES");
        hashedText = ByteSource.Util.bytes(key.getEncoded()).toBase64();
        return hashedText;
    }
}
