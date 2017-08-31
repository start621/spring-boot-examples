package com.neo.sevice;

import com.neo.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * spring-boot-shiro-RoleService
 * 角色管理接口
 *
 * @author yh
 * @since 0.0.1 2017-08-31 22:20
 */
public interface RoleService {

    /**
     * 通过name查找角色信息
     */
    public Role findByName(String roleName);

    /**
     * 创建角色
     */
    public Role create(Role role);

    /**
     * 批量创建
     */
    public List<Role> BatchCreate(List<Role> roles);

    /**
     * 获取所有角色信息
     */
    public List<Role> findAll();

    /**
     * 统计当前角色数量
     */
    public long count();

    /**
     * 删除角色
     */
    public void delete(String roleName);

    /**
     * 批量角色
     */
    public void batchDelete();

    /**
     * 更新角色信息
     */
    public Role update(Role role);

    /**
     * 分页获取角色信息
     */
    // Pageable (int page=1, size=10; Sort sort = new Sort(Direction.DES, "id")) 使用
    public Page<Role> findAll(Pageable pageable);

}
