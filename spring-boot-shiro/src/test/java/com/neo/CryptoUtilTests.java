package com.neo;

import com.neo.common.CryptoUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * spring-boot-shiro-CryptoUtilTests
 * 测试加解密工具类
 *
 * @author yh
 * @since 0.0.1 2017-08-30 22:51
 */
@Slf4j
@RunWith(SpringRunner.class)
public class CryptoUtilTests {

    @Test
    public void test() throws Exception {
        String salt = new String(CryptoUtil.generateSalt());

        String password = "123456";

        String encryptText = CryptoUtil.passwordEncrypt(password);

        boolean result = CryptoUtil.validatePassword(password, encryptText);

        Assert.assertTrue(result);
    }
}
