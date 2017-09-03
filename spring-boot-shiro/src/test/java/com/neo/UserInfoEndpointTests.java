package com.neo;

import com.neo.entity.Role;
import com.neo.entity.UserInfo;
import com.neo.entity.UserStatus;
import com.neo.entity.UserType;
import com.neo.sevice.RoleService;
import com.neo.sevice.UserInfoService;
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
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
public class UserInfoEndpointTests {

    // @Autowired
    // private UserInfoDao userInfoDao;
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RoleService roleService;

    @Autowired
    // 集成测试验证，模拟整个web应用的上下文环境，否则使用standalone会出现controller层 autowired的bean为空
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private static final String BASE_PATH = "/userManagement/users";

    @Before
    // standalone模式用于做单元测试验证
    // public void init() {
    //     mockMvc = MockMvcBuilders.standaloneSetup(new UserInfoEndpoint()).build();
    // }
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetUsers() throws Exception{

        List<Role> roles = new ArrayList<>();
        Role admin = roleService.findByName("admin");
        roles.add(admin);

        UserInfo user1 = new UserInfo();
        user1.setUsername("test01");
        user1.setPassword("123456");
        user1.setEmail("test01@126.com");
        user1.setDescription("测试01");
        user1.setCreateTime(new Date());
        user1.setLastModifyTime(new Date());
        user1.setOnlineMaxCount(1);
        user1.setOutDate(90);
        user1.setState(UserStatus.NEW);
        user1.setType(UserType.HUMAN_MACHINE);
        user1.setRoleList(roles);

        userInfoService.create(user1);
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
