package com.neo.sevice.impl;

import com.neo.dao.UserInfoDao;
import com.neo.entity.UserInfo;
import com.neo.sevice.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    private static final int DEFAULT_MAX_ONLINE = 5;

    @Override
    public UserInfo findByUsername(String username) {
        System.out.println("UserInfoServiceImpl.findByUsername()");
        return userInfoDao.findByUsername(username);
    }

    @Override
    @Transactional
    public UserInfo create(UserInfo userInfo) {
        return userInfoDao.save(userInfo);
    }

    @Override
    public List<UserInfo> BatchCreate(List<UserInfo> users) {
        return userInfoDao.save(users);
    }

    @Override
    public List<UserInfo> findAll() {
        return userInfoDao.findAll();
    }

    @Override
    public long count() {
        return userInfoDao.count();
    }

    @Override
    public void delete(String username) {
        userInfoDao.deleteByUsername(username);
    }

    @Override
    public void batchDelete() {
        userInfoDao.deleteAll();
    }

    @Override
    public UserInfo update(UserInfo userInfo) {
        return userInfoDao.save(userInfo);
    }

    @Override
    public List<UserInfo> findByOnlineMaxCountGreaterThan() {

        return userInfoDao.findByOnlineMaxCountGreaterThan(DEFAULT_MAX_ONLINE);
    }

    @Override
    public List<UserInfo> findByLockedIpsNotNull() {
        return userInfoDao.findByLockedIpsNotNull();
    }

    @Override
    public List<UserInfo> findByUsernameOrEmailIgnoreCase(String usernanme, String email) {
        return userInfoDao.findByUsernameOrEmailIgnoreCase(usernanme, email);
    }

    @Override
    public List<UserInfo> findByEmailEndingWith(String emailEnding) {
        return userInfoDao.findByEmailEndingWith(emailEnding);
    }

    @Override
    public Page<UserInfo> findAll(Pageable pageable) {
        if (pageable == null || pageable.getPageNumber() == 0 || pageable.getPageSize() == 0
                || pageable.getSort() == null)
        {
            int page = 1, size = 5;
            Sort sort = new Sort(Sort.Direction.ASC, "id");
            pageable = new PageRequest(page, size, sort);
        }
        return userInfoDao.findAll(pageable);
    }

    @Override
    public Page<UserInfo> findByUsername(Pageable pageable, String username) {
        if (pageable == null || pageable.getPageNumber() == 0 || pageable.getPageSize() == 0
                || pageable.getSort() == null)
        {
            int page = 1, size = 5;
            Sort sort = new Sort(Sort.Direction.ASC, "id");
            pageable = new PageRequest(page, size, sort);
        }
        return userInfoDao.findByUsername(pageable, username);
    }

    @Override
    public List<UserInfo> findTop10ByUsernameOrderById(String username) {
        return userInfoDao.findTop10ByUsernameOrderById(username);
    }
}