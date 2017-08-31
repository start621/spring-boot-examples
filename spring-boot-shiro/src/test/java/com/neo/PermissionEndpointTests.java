package com.neo;

import com.neo.entity.Permission;
import com.neo.sevice.PermissionService;
import com.neo.web.PermissionEndpoint;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;
import java.util.TreeSet;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * spring-boot-shiro-PermissionEndpointTests
 * 权限点测试用例
 *
 * @author yh
 * @since 0.0.1 2017-08-31 23:20
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class PermissionEndpointTests {

    @Autowired
    private PermissionService permissionService;

    private MockMvc mockMvc;
    private static final String BASE_PATH = "/userManagement/permissions";

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(new PermissionEndpoint()).build();
    }

    @Test
    public void testAddPermission() throws Exception {
        Set<String> privileges = new TreeSet<>();
        privileges.add("admin:*");
        privileges.add("test:get");
        Permission permission = new Permission("user", "/users/**", privileges);
        permissionService.create(permission);

        Assert.assertSame(1L, permissionService.count());

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // @After
    // public void delete() {
    //     permissionService.batchDelete();
    // }
}
