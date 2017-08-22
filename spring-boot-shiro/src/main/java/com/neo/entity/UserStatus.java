package com.neo.entity;

/**
 * spring-boot-shiro-UserStatus
 * 用户状态
 *
 * @author y00355492
 * @since 0.0.1 2017-08-22 16:32
 */

public enum UserStatus {

    //0.新用户
    NEW,

    //1.正常用户
    NORMAL,

    //2.停用用户
    DEACTIVED,

    //3.被锁定用户，锁定策略：根据IP锁定
    LOCKED,

}
