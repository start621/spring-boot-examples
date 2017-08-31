package com.neo.sevice.impl;

import com.neo.dao.PermissionDao;
import com.neo.entity.Permission;
import com.neo.sevice.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * spring-boot-shiro-PermissionServiceImpl
 * 权限点接口实现
 *
 * @author yh
 * @since 0.0.1 2017-08-31 22:38
 */

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionDao permissionDao;

    @Override
    public Permission findByName(String permissionName) {
        return permissionDao.findByName(permissionName);
    }

    @Override
    public Permission create(Permission permission) {
        return permissionDao.save(permission);
    }

    @Override
    public List<Permission> BatchCreate(List<Permission> permissions) {
        return permissionDao.save(permissions);
    }

    @Override
    public List<Permission> findAll() {
        return permissionDao.findAll();
    }

    @Override
    public long count() {
        return permissionDao.count();
    }

    @Override
    public void delete(String name) {
        permissionDao.deleteByName(name);
    }

    @Override
    public void batchDelete() {
        permissionDao.deleteAll();
    }

    @Override
    public Permission update(Permission permission) {
        return permissionDao.save(permission);
    }

    @Override
    public Page<Permission> findAll(Pageable pageable) {
        if (pageable == null || pageable.getPageNumber() == 0 || pageable.getPageSize() == 0
                || pageable.getSort() == null) {
            int page = 1, size = 5;
            Sort sort = new Sort(Sort.Direction.ASC, "id");
            pageable = new PageRequest(page, size, sort);
        }
        return permissionDao.findAll(pageable);
    }
}
