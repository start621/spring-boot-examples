package com.neo.dao;

import com.neo.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * spring-boot-shiro-PermissionDao
 * 权限点dao
 *
 * @author yh
 * @since 0.0.1 2017-08-31 22:37
 */
public interface PermissionDao extends JpaRepository<Permission, String> {

    public Permission findByName(String name);

    public void deleteByName(String name);
}
