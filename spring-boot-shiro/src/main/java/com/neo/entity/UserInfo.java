package com.neo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class UserInfo implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    @NonNull
    @Column(nullable = false, unique =true)
    private String username;//帐号

    @NonNull
    private String email;//邮箱

    @NonNull
    private String description;

    @NonNull
    @Column(nullable = false)
    private String password; //密码;

    // private String salt;//加密密码的盐
    @NonNull
    @Enumerated(EnumType.STRING)
    private UserStatus state;//用户状态

    @NonNull
    // 账号类型，HUMAN_MACHINE:人机账号，MACHINE_MACHINE:机机账号
    @Enumerated(EnumType.STRING)
    private UserType type;

    @ManyToMany(fetch= FetchType.EAGER)//立即从数据库中进行加载数据;
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns ={@JoinColumn(name = "role_id") })
    private List<Role> roleList;// 一个用户具有多个角色

    @NonNull
    @Column //(nullable = false)
    private Date createTime;

    @NonNull
    @Column  //(nullable = false)
    private Date lastModifyTime;

    @Column
    private int outDate;

    @ElementCollection(fetch = FetchType.EAGER) //由于lockedIps只会每个ip出现一次，
    // 所以应该用set，同时如果有多个list会有cannot simultaneously fetch multiple bags问题；
    // 另外如果fetchType是lazy的话，会有JPA Lazy fetch not working and throwing
    // lazyInitializationException
    // @Fetch(FetchMode.SUBSELECT) 增加一个select而不用仅默认的left outer join，list不会有问题
    private Set<String> lockedIps; //用户在那些IP被锁定

    // 最大同时在线数, 0表示不限制在线数
    @Column
    private int onlineMaxCount;

    @Column
    private String validateCode; //验证码

    @Column
    private String salt;
    /**
     * 密码盐.
     * @return ddd
     */
    // public String getCredentialsSalt(){
        // return this.username+this.salt;
    // }
    //重新对盐重新进行了定义，用户名+salt，这样就更加不容易被破解
}