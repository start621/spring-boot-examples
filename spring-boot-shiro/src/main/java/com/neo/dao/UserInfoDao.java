package com.neo.dao;

import com.neo.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserInfoDao extends JpaRepository<UserInfo,Long> {

    //通过username查找用户信息
    public UserInfo findByUsername(String username);

    // 最大在线数大于1的用户数
    public List<UserInfo> findByOnlineMaxCountGreaterThan(int onlineNum);

    // 查询锁定IP不为null的用户
    public List<UserInfo> findByLockedIpsNotNull();

    // 根据邮箱或用户名查询用户
    public List<UserInfo> findByUsernameOrEmailIgnoreCase(String username, String email);

    // 查询邮箱为126邮箱的用户
    public List<UserInfo> findByEmailEndingWith(String emailEnding);

    public Page<UserInfo> findByUsername(Pageable pageable, String username);

    public  List<UserInfo> findTop10ByUsernameOrderById(String username);

    public void deleteById(String id);

    public void deleteByUsername(String username);

    @Transactional
    @Modifying
    @Query("update UserInfo u set u.description=?1 where u.id = ?2")
    public UserInfo updateDescription(String description, String id);

}