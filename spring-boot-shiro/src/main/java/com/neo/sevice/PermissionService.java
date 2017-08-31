package com.neo.sevice;

import com.neo.entity.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * spring-boot-shiro-PermissionService
 * 权限点管理接口
 *
 * @author yh
 * @since 0.0.1 2017-08-31 22:28
 */

public interface PermissionService {

    /**
     * 通过name查找权限点信息
     */
    public Permission findByName(String permissionName);


    /**
     * 创建新权限点
     */
    public Permission create(Permission permission);

    /**
     * 批量创建
     */
    public List<Permission> BatchCreate(List<Permission> permissions);

    /**
     * 获取所有权限点
     */
    public List<Permission> findAll();

    /**
     * 统计当前权限点数量
     */
    public long count();

    /**
     * 删除权限点
     */
    public void delete(String name);

    /**
     * 批量删除权限点
     */
    public void batchDelete();

    /**
     * 更新权限点信息
     */
    public Permission update(Permission permission);

    /**
     * 分页获取权限点信息
     */
    // Pageable (int page=1, size=10; Sort sort = new Sort(Direction.DES, "id")) 使用
    public Page<Permission> findAll(Pageable pageable);
}
