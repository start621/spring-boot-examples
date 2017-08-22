package com.neo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class UserInfo implements Serializable {
    @Id
    @GeneratedValue
    private Integer uid;

    @Column(nullable = false, unique =true)
    private String username;//帐号

    private String name;//名称（昵称或者真实姓名，不同系统不同定义）

    @Column(nullable = false)
    private String password; //密码;

    private String salt;//加密密码的盐

    private byte state;//用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定.

    @ManyToMany(fetch= FetchType.EAGER)//立即从数据库中进行加载数据;
    @JoinTable(name = "SysUserRole", joinColumns = { @JoinColumn(name = "uid") }, inverseJoinColumns ={@JoinColumn(name = "roleId") })
    private List<Role> roleList;// 一个用户具有多个角色

    @Column(nullable = false)
    private Date createTime;

    @Column(nullable = false)
    private Date lastModifyTime;

    @Column
    private String outDate;

    @Column
    private String validateCode; //验证码

    /**
     * 密码盐.
     * @return ddd
     */
    public String getCredentialsSalt(){
        return this.username+this.salt;
    }
    //重新对盐重新进行了定义，用户名+salt，这样就更加不容易被破解
}