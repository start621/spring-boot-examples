package com.neo.sevice;

import com.alibaba.fastjson.JSON;
import com.neo.common.RedisUtil;
import com.neo.entity.RedisInfoDetail;
import com.neo.entity.RedisOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.util.Slowlog;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * spring-boot-shiro-RedisService
 * 获取redis当前状态
 *
 * @author yh
 * @since 0.0.1 2017-09-05 23:16
 */

@Service
public class RedisService {

    @Autowired
    RedisUtil redisUtil;

    public List<RedisInfoDetail> getRedisInfo() {
        //获取redis服务器信息
        String info = redisUtil.getRedisInfo();
        List<RedisInfoDetail> ridList = new ArrayList<>();
        String[] splits = info.split("\n");
        RedisInfoDetail rif;
        if (splits.length > 0) {
            for (int i = 0; i < splits.length; i++) {
                rif = new RedisInfoDetail();
                String s = splits[i];
                String[] str = s.split(":");
                if (str.length > 1) {
                    String key = str[0];
                    String value = str[1];
                    rif.setKey(key);
                    rif.setValue(value);
                    ridList.add(rif);
                }
            }
        }
        return ridList;
    }

    //获取redis日志列表
    public List<RedisOperation> getLogs(long entries) {
        List<Slowlog> list = redisUtil.getLogs(entries);
        List<RedisOperation> opList = null;
        RedisOperation op;
        boolean flag = false;
        if (list != null && list.size() > 0) {
            opList = new LinkedList<>();
            for (Slowlog sl : list) {
                String args = JSON.toJSONString(sl.getArgs());
                if (args.equals("[\"PING\"]") || args.equals("[\"SLOWLOG\",\"get\"]") || args.equals("[\"DBSIZE\"]") || args.equals("[\"INFO\"]")) {
                    continue;
                }
                op = new RedisOperation();
                flag = true;
                op.setId(sl.getId());
                op.setExecuteTime(getDateStr(sl.getTimeStamp() * 1000));
                op.setUsedTime(sl.getExecutionTime() / 1000.0 + "ms");
                op.setArgs(args);
                opList.add(op);
            }
        }
        if (flag)
            return opList;
        else
            return null;
    }

    //获取日志总数
    public Long getLogLen() {
        return redisUtil.getLogsLen();
    }

    //清空日志
    public String logEmpty() {
        return redisUtil.logEmpty();
    }

    //获取当前数据库中key的数量
    public Map<String, Object> getKeysSize() {
        long dbSize = redisUtil.dbSize();
        Map<String, Object> map = new HashMap<>();
        map.put("create_time", new Date().getTime());
        map.put("dbSize", dbSize);
        return map;
    }

    //获取当前redis使用内存大小情况
    public Map<String, Object> getMemoryInfo() {
        String[] strs = redisUtil.getRedisInfo().split("\n");
        Map<String, Object> map = null;
        for (int i = 0; i < strs.length; i++) {
            String s = strs[i];
            String[] detail = s.split(":");
            if (detail[0].equals("used_memory")) {
                map = new HashMap<>();
                map.put("used_memory", detail[1].substring(0, detail[1].length() - 1));
                map.put("create_time", new Date().getTime());
                break;
            }
        }
        return map;
    }

    private String getDateStr(long timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date(timestamp));
    }
}
