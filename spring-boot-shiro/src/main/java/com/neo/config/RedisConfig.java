package com.neo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * spring-boot-shiro-RedisConfig
 * 配置redis作为spring的cache，通过注解的方式完成spring的配置
 * 其他地方可以通过该工厂获取redis的链接
 *
 * @author yh
 * @since 0.0.1 2017-09-05 22:14
 */
@Slf4j
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.pool.max-wait}")
    private long maxWaitMillis;

    @Bean
    public JedisPool jedisPoolFactory() {
        JedisPoolConfig config = new JedisPoolConfig();
        log.debug("get the jedis pool with config: [host:{}], [port:{}], [max-idle:{}], [max-wait:{}], [timeout:{}]", host, port, maxIdle, maxWaitMillis, timeout);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWaitMillis);

        return new JedisPool(config, host, port, timeout);
    }
}
