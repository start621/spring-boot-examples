package com.neo.web;

import com.neo.entity.UserInfo;
import com.neo.sevice.UserInfoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/userManagement")
public class UserInfoController {

    @Autowired
    UserInfoService userService;
    //// TODO: 2017/8/25 添加入参校验
    /**
     * 用户查询.
     * @return all user_info
     */
    @ApiOperation(value = "用户查询")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    //@RequiresPermissions("userInfo:view")//权限管理;
    public List<UserInfo> getAllUsers(){
        log.info("---> 入口");
        return userService.findAll();
    }

    /**
     * 批量创建用户接口
     * @param userInfoList 输入用户信息
     * @return 新建用户信息
     */
    @ApiOperation(value = "批量创建用户接口")
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public List<UserInfo> createUsers(@RequestBody List<UserInfo> userInfoList) {
        return userService.BatchCreate(userInfoList);
    }

    /**
     * 批量删除用户
     */
    @ApiOperation(value = "批量删除用户")
    @RequestMapping(value = "/users", method = RequestMethod.DELETE)
    public void deleteUsers(){
        userService.batchDelete();
    }

    /**
     * 用户添加;
     * @return 返回新建用户名
     */
    @ApiOperation(value = "用户添加")
    @RequestMapping(value = "/users/user", method = RequestMethod.POST)
    //@RequiresPermissions("userInfo:add")//权限管理;
    public String userInfoAdd(@RequestBody UserInfo userInfo){
        log.info("---> user add");
        return userService.create(userInfo).getUsername();
    }

    @ApiOperation(value = "用户信息修改")
    @RequestMapping(value = "/users/user", method = RequestMethod.PUT)
    //@RequiresPermissions("userInfo:add")//权限管理;
    public String userInfoUpdate(@RequestBody UserInfo userInfo){
        return userService.update(userInfo).getUsername();
    }

    @ApiOperation(value = "根据用户名查找")
    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    public UserInfo getUserInfo(@PathVariable String username) {
        return  userService.findByUsername(username);
    }
    /**
     * 用户删除;
     * @return message
     */
    @ApiOperation(value = "根据用户Id删除")
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    //@RequiresPermissions("userInfo:del")//权限管理;
    public String  userInfoDel(@PathVariable String id){
        userService.delete(id);
        return "user: {" + id + "} delete success.";
    }
}