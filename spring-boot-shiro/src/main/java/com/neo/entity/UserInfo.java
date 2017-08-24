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
    private Integer id;

    @Column(nullable = false, unique =true)
    private String username;//帐号

    private String email;//邮箱

    private String description;

    @Column(nullable = false)
    private String password; //密码;

    // private String salt;//加密密码的盐

    @Enumerated(EnumType.STRING)
    private UserStatus state;//用户状态

    // 账号类型，HUMAN_MACHINE:人机账号，MACHINE_MACHINE:机机账号
    @Enumerated(EnumType.STRING)
    private UserType type;

    @ManyToMany(fetch= FetchType.EAGER)//立即从数据库中进行加载数据;
    @JoinTable(name = "SysUserRole", joinColumns = { @JoinColumn(name = "uid") },
            inverseJoinColumns ={@JoinColumn(name = "roleId") })
    private List<Role> roleList;// 一个用户具有多个角色

    @Column(nullable = false)
    private Date createTime;

    @Column(nullable = false)
    private Date lastModifyTime;

    @Column
    private String outDate;

    @ElementCollection
    private List<String> lockedIps; //用户在那些IP被锁定

    // 最大同时在线数, 0表示不限制在线数
    @Column
    private int onlineMaxCount;

    @Column
    private String validateCode; //验证码

    /**
     * 密码盐.
     * @return ddd
     */
    // public String getCredentialsSalt(){
        // return this.username+this.salt;
    // }
    //重新对盐重新进行了定义，用户名+salt，这样就更加不容易被破解
}