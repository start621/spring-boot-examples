package com.neo.sevice;

import com.neo.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserInfoService {
    /**通过username查找用户信息;*/
    public UserInfo findByUsername(String username);


    //测试JpaRepository提供的能力

    /**
     * 创建用户
     */
    public UserInfo create(UserInfo userInfo) throws Exception;

    /**
     * 批量创建
     */
    public List<UserInfo> BatchCreate(List<UserInfo> users) throws Exception;

    /**
     * 获取所有用户
     */
    public List<UserInfo> findAll();

    /**
     * 统计当前用户数量
     */
    public long count();

    /**
     * 删除用户
     */
    public void delete(String username);

    /**
     * 批量删除
     */
    public void batchDelete();

    /**
     * 更新用户信息
     */
    public UserInfo update(UserInfo userInfo);

    /**
     * 自定义简单查询
     */
    // 最大在线数大于1的用户数
    public List<UserInfo> findByOnlineMaxCountGreaterThan();

    // 查询锁定IP不为null的用户
    public List<UserInfo> findByLockedIpsNotNull();

    // 根据邮箱或用户名查询用户
    public List<UserInfo> findByUsernameOrEmailIgnoreCase(String usernanme, String email);

    // 查询邮箱为126邮箱的用户
    public List<UserInfo> findByEmailEndingWith(String emailEnding);

    /**
     * 分页获取用户
     */
    // Pageable (int page=1, size=10; Sort sort = new Sort(Direction.DES, "id")) 使用
    public Page<UserInfo> findAll(Pageable pageable);

    public Page<UserInfo> findByUsername(Pageable pageable, String username);
    /**
     * 限制查询
     */
    public  List<UserInfo> findTop10ByUsernameOrderById(String username);
    /**
     * 自定义SQL查询
     */
    // public UserInfo updateDescription(String description);

    /**
     * 多表查询,合并多张表的查询结果
     * http://www.ityouknow.com/springboot/2016/08/20/springboot(%E4%BA%94)-spring-data-jpa%E7%9A%84%E4%BD%BF%E7%94%A8.html
     */
}