package com.neo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class Permission implements Serializable {
    @Id@GeneratedValue
    private Integer id;//主键.

    @NonNull
    @Column(nullable = false, unique = true)
    private String name;//名称.

    //@Column(columnDefinition="enum('menu','button')")
    //private String resourceType;//资源类型，[menu|button], 对应页面上的类型view或者button
    @NonNull
    private String url;//资源路径.

    @NonNull
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> privileges; //权限字符串, module:action_type, menu例子：role:*，button例子：role:create,role:update,role:delete,role:view

    // @ManyToMany(mappedBy = "permissions")
    // private List<Role> roles;

    private Long parentId; //父编号
    private String parentIds; //父编号列表
    private Boolean available = Boolean.FALSE; //标识权限点是否启用

}