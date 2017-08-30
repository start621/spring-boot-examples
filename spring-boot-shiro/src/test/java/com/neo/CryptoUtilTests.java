package com.neo;

import com.neo.common.CryptoUtil;
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

@RunWith(SpringRunner.class)
public class CryptoUtilTests {
    private String salt;
    @Test
    public void test(){
        salt = new String(CryptoUtil.generateSalt());
    }
}
