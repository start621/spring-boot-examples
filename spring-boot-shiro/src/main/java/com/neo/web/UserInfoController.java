package com.neo.web;

import com.neo.entity.UserInfo;
import com.neo.sevice.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/userManagement/users")
public class UserInfoController {

    @Autowired
    UserInfoService userService;
    //// TODO: 2017/8/25 添加入参校验
    /**
     * 用户查询.
     * @return all user_info
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    //@RequiresPermissions("userInfo:view")//权限管理;
    public List<UserInfo> getAllUsers(){
        return userService.findAll();
    }

    /**
     * 批量创建用户接口
     * @param userInfoList 输入用户信息
     * @return 新建用户信息
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public List<UserInfo> createUsers(List<UserInfo> userInfoList) {
        return userService.BatchCreate(userInfoList);
    }

    /**
     * 批量删除用户
     */
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public void deleteUsers(){
        userService.batchDelete();
    }
    /**
     * 用户添加;
     * @return 返回新建用户名
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    //@RequiresPermissions("userInfo:add")//权限管理;
    public String userInfoAdd(UserInfo userInfo){
        return userService.create(userInfo).getUsername();
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    //@RequiresPermissions("userInfo:add")//权限管理;
    public String userInfoUpdate(UserInfo userInfo){
        return userService.update(userInfo).getUsername();
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public UserInfo getUserInfo(@PathVariable String username) {
        return  userService.findByUsername(username);
    }
    /**
     * 用户删除;
     * @return message
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    //@RequiresPermissions("userInfo:del")//权限管理;
    public String  userInfoDel(@PathVariable String id){
        userService.delete(id);
        return "user: {" + id + "} delete success.";
    }
}