package com.neo.common;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
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
        log.debug("start generate the salt.");

        byte[] salt = new byte[SALT_LENGTH];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);

        return salt;
    }

    private static String pbkdf2Encrypt(String plainText, byte[] salt) throws
            NoSuchAlgorithmException, InvalidKeySpecException {
        String hashedText;

        // 获取密钥生成工厂类
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
        // 转换密钥材料
        KeySpec keySpec = new PBEKeySpec(plainText.toCharArray(), salt, ITERATION, KEY_LENGTH);
        byte[] key = factory.generateSecret(keySpec).getEncoded();

        hashedText = Hex.encodeHexString(key);
        return hashedText;
    }

    public static String passwordEncrypt(String plainPassword) throws
            NoSuchAlgorithmException, InvalidKeySpecException {
        log.debug("start encrypt the password.");
        byte[] salt = generateSalt();
        String encryptPassword = pbkdf2Encrypt(plainPassword, salt);
        return Hex.encodeHexString(salt) + ":" + encryptPassword;
    }

    public static boolean validatePassword(String submittedPassword, String encryptPassword) throws
            DecoderException, NoSuchAlgorithmException, InvalidKeySpecException {
        log.debug("start validate the password.");
        String toBeValidate = "";
        if (!Strings.isNullOrEmpty(encryptPassword)) {
            String salt = encryptPassword.split(":")[0];
            toBeValidate = pbkdf2Encrypt(submittedPassword, Hex.decodeHex(salt.toCharArray()));
        }
        return encryptPassword.split(":")[1].equals(toBeValidate);
    }
}
