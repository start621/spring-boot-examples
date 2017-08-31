package com.neo.dao;

import com.neo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * spring-boot-shiro-RoleDao
 * 角色dao层
 *
 * @author yh
 * @since 0.0.1 2017-08-31 22:17
 */
public interface RoleDao extends JpaRepository<Role, String> {

    public Role findByName(String name);

    public void deleteByName(String name);
}
