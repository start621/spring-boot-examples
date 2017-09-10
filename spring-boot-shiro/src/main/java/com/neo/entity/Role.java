package com.neo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
//注意加上序列化，不然redis中读取出来之后无法反序列化，报的invalid bulk length异常。。。
public class Role implements Serializable {

    @Id
    @GeneratedValue
    private Integer id; // 编号

    @NonNull
    @Column(nullable = false, unique = true)
    private String name; // 角色标识程序中判断使用,如"admin",这个是唯一的:

    @NonNull
    private String description; // 角色描述,UI界面显示使用

    private Boolean available = Boolean.FALSE; // 是否可用,如果不可用将不会添加给用户

    //角色 -- 权限关系：多对多关系;
    @NonNull
    @ManyToMany(fetch= FetchType.EAGER)
    @JoinTable(name="role_permission", joinColumns={@JoinColumn(name="role_id")},
            inverseJoinColumns={@JoinColumn(name="permission_id")})
    private List<Permission> permissions;

    // 用户 - 角色关系定义;  todo 是否需要双向关联,要么直接去掉，要么通过修改toString()方法防止递归调用
    // @ManyToMany(mappedBy = "roleList")
    // private List<UserInfo> userInfos;// 一个角色对应多个用户

}