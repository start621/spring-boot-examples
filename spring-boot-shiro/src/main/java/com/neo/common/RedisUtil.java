package com.neo.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.Slowlog;

import java.util.List;

@Component
public class RedisUtil {

    @Autowired
    JedisPool jedisPool;

    // 获取redis 服务器信息
    public String getRedisInfo() {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Client client = jedis.getClient();
            client.info();
            return client.getBulkReply();
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    // 获取日志列表
    public List<Slowlog> getLogs(long entries) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.slowlogGet(entries);
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    // 获取日志条数
    public Long getLogsLen() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.slowlogLen();
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    // 清空日志
    public String logEmpty() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.slowlogReset();
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    // 获取占用内存大小
    public Long dbSize() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // TODO 配置redis服务信息
            Client client = jedis.getClient();
            client.dbSize();
            return client.getIntegerReply();
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }
}
