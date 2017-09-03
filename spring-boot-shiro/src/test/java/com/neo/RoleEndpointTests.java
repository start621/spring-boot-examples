package com.neo;

import com.neo.entity.Permission;
import com.neo.entity.Role;
import com.neo.sevice.PermissionService;
import com.neo.sevice.RoleService;
import org.junit.Assert;
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
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * spring-boot-shiro-RoleEndpointTests
 * 角色管理接口测试
 *
 * @author yh
 * @since 0.0.1 2017-08-31 23:35
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class RoleEndpointTests {

    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private WebApplicationContext webApplicationContext;


    private MockMvc mockMvc;
    private static final String BASE_PATH = "/userManagement/roles";

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testAddRole() throws Exception {
        List<Permission> permissions = new ArrayList<>();
        Permission userManage = permissionService.findByName("user");
        permissions.add(userManage);

        Role role = new Role("admin", "超级管理员", permissions);
        roleService.create(role);
        Assert.assertSame(1L, roleService.count());

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
