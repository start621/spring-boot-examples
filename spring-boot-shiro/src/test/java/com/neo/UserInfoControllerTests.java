package com.neo;

import com.neo.entity.UserInfo;
import com.neo.entity.UserStatus;
import com.neo.entity.UserType;
import com.neo.sevice.UserInfoService;
import com.neo.web.UserInfoController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * spring-boot-shiro-UserInfoControllerTests
 * 用户管理测试用例
 * @author yh
 * @since 0.0.1 2017-08-25 21:39
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UserInfoControllerTests {

    // @Autowired
    // private UserInfoDao userInfoDao;
    @Autowired
    private UserInfoService userInfoService;

    private MockMvc mockMvc;
    private static final String BASE_PATH = "/userManagement/users";

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserInfoController()).build();
    }

    @Test
    public void testGetUsers() throws Exception{
        UserInfo user1 = new UserInfo();
        user1.setUsername("admin");
        user1.setPassword("123456");
        user1.setEmail("test@126.com");
        user1.setDescription("管理员");
        user1.setCreateTime(new Date());
        user1.setLastModifyTime(new Date());
        user1.setOnlineMaxCount(1);
        user1.setOutDate(90);
        user1.setState(UserStatus.NEW);
        user1.setType(UserType.HUMAN_MACHINE);

        userInfoService.create(user1);
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
