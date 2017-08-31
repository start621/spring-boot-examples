package com.neo.web;

import com.neo.entity.Role;
import com.neo.sevice.RoleService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * spring-boot-shiro-RoleEndpoint
 * 角色管理
 *
 * @author yh
 * @since 0.0.1 2017-08-31 22:16
 */

@Slf4j
@RestController
@RequestMapping("/userManagement")
public class RoleEndpoint {

    @Autowired
    private RoleService roleService;

    /**
     * 角色查询.
     *
     * @return 返回所有角色信息
     */
    @ApiOperation(value = "角色查询")
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    //@RequiresPermissions("userInfo:view")//权限管理;
    public List<Role> getAllRoles() {
        log.info("---> 入口");
        return roleService.findAll();
    }

    /**
     * 根据角色名查找角色
     *
     * @param name 角色名
     * @return 角色信息
     */
    @ApiOperation(value = "根据角色名查找角色")
    @RequestMapping(value = "/roles/{name}", method = RequestMethod.GET)
    public Role getRole(@PathVariable String name) {
        return roleService.findByName(name);
    }

    /**
     * 新建角色;
     *
     * @return 返回新建角色名
     */
    @ApiOperation(value = "新建角色")
    @RequestMapping(value = "/roles/role", method = RequestMethod.POST)
    //@RequiresPermissions("userInfo:add")//权限管理;
    public String roleAdd(@RequestBody Role role) {
        log.info("---> role add");
        role = roleService.create(role);
        return role.getName();
    }

    /**
     * 修改角色信息
     *
     * @param role 更新的角色信息
     * @return 返回更新后的角色名
     */
    @ApiOperation(value = "用户信息修改")
    @RequestMapping(value = "/roles/role", method = RequestMethod.PUT)
    //@RequiresPermissions("userInfo:add")//权限管理;
    public String roleUpdate(@RequestBody Role role) {
        return roleService.update(role).getName();
    }

    /**
     * 根据角色名删除;
     *
     * @return message
     */
    @ApiOperation(value = "根据角色名删除")
    @RequestMapping(value = "/roles/{name}", method = RequestMethod.DELETE)
    //@RequiresPermissions("userInfo:del")//权限管理;
    public String roleDel(@PathVariable String name) {
        roleService.delete(name);
        return "role: {" + name + "} delete success.";
    }
}
