package com.neo.entity;

import lombok.Data;

/**
 * spring-boot-shiro-UserStatus
 * 用户状态
 *
 * @author y00355492
 * @since 0.0.1 2017-08-22 16:32
 */

@Data
public class RedisOperation {

    private long id;//slowlog唯一编号id

    private String executeTime;// 查询的时间戳

    private String usedTime;// 查询的耗时（微妙），如表示本条命令查询耗时47微秒

    private String args;// 查询命令，完整命令为 SLOWLOG GET，slowlog最多保存前面的31个key和128字符
}
