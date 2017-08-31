package com.neo.web;

import com.neo.entity.Permission;
import com.neo.sevice.PermissionService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * spring-boot-shiro-PermissionEndpoint
 * 权限点管理端点
 *
 * @author yh
 * @since 0.0.1 2017-08-31 22:52
 */

@Slf4j
@RestController
@RequestMapping("/userManagement")
public class PermissionEndpoint {

    @Autowired
    private PermissionService permissionService;

    /**
     * 权限点查询.
     *
     * @return 返回所有权限点信息
     */
    @ApiOperation(value = "权限点查询")
    @RequestMapping(value = "/permissions", method = RequestMethod.GET)
    //@RequiresPermissions("userInfo:view")//权限管理;
    public List<Permission> getAllPermissions() {
        log.info("---> 入口");
        return permissionService.findAll();
    }

    /**
     * 根据权限点名称查找权限点
     *
     * @param name 权限点名称
     * @return 权限点信息
     */
    @ApiOperation(value = "根据权限点名称查找权限点")
    @RequestMapping(value = "/permissions/{name}", method = RequestMethod.GET)
    public Permission getPermission(@PathVariable String name) {
        return permissionService.findByName(name);
    }

    /**
     * 新建权限点;
     *
     * @return 返回新建权限点名称
     */
    @ApiOperation(value = "新建权限点")
    @RequestMapping(value = "/permissions/permission", method = RequestMethod.POST)
    //@RequiresPermissions("userInfo:add")//权限管理;
    public String permissionAdd(@RequestBody Permission permission) {
        log.info("---> role add");
        permission = permissionService.create(permission);
        return permission.getName();
    }

    /**
     * 修改权限点信息
     *
     * @param permission 更新的权限点信息
     * @return 返回更新后的权限点名称
     */
    @ApiOperation(value = "权限点信息修改")
    @RequestMapping(value = "/permissions/permission", method = RequestMethod.PUT)
    //@RequiresPermissions("userInfo:add")//权限管理;
    public String PermissionUpdate(@RequestBody Permission permission) {
        return permissionService.update(permission).getName();
    }

    /**
     * 根据权限点名称删除;
     *
     * @return message
     */
    @ApiOperation(value = "根据权限点名称删除")
    @RequestMapping(value = "/permissions/{name}", method = RequestMethod.DELETE)
    //@RequiresPermissions("userInfo:del")//权限管理;
    public String PermissionDel(@PathVariable String name) {
        permissionService.delete(name);
        return "permission: {" + name + "} delete success.";
    }
}
