package com.neo.sevice.impl;

import com.neo.dao.RoleDao;
import com.neo.entity.Role;
import com.neo.sevice.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * spring-boot-shiro-RoleServiceImpl
 * 角色管理接口实现
 *
 * @author yh
 * @since 0.0.1 2017-08-31 22:49
 */

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Role findByName(String roleName) {
        return roleDao.findByName(roleName);
    }

    @Override
    public Role create(Role role) {
        return roleDao.save(role);
    }

    @Override
    public List<Role> BatchCreate(List<Role> roles) {
        return roleDao.save(roles);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public long count() {
        return roleDao.count();
    }

    @Override
    public void delete(String roleName) {
        roleDao.deleteByName(roleName);
    }

    @Override
    public void batchDelete() {
        roleDao.deleteAll();
    }

    @Override
    public Role update(Role role) {
        return roleDao.save(role);
    }

    @Override
    public Page<Role> findAll(Pageable pageable) {
        if (pageable == null || pageable.getPageNumber() == 0 || pageable.getPageSize() == 0
                || pageable.getSort() == null) {
            int page = 1, size = 5;
            Sort sort = new Sort(Sort.Direction.ASC, "id");
            pageable = new PageRequest(page, size, sort);
        }
        return roleDao.findAll(pageable);
    }
}
