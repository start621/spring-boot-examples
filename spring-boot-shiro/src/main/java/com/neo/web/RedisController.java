package com.neo.web;

import com.alibaba.fastjson.JSON;
import com.neo.entity.RedisInfoDetail;
import com.neo.entity.RedisOperation;
import com.neo.sevice.RedisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * spring-boot-shiro-RedisController
 * 显示当前redis的集群状态
 *
 * @author yh
 * @since 0.0.1 2017-09-05 23:15
 */
@Controller
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private RedisService redisService;

    //跳转到监控页面
    @RequestMapping(value = "/redisMonitor")
    public String redisMonitor(Model model) {
        //获取redis的info
        List<RedisInfoDetail> ridList = redisService.getRedisInfo();
        //获取redis的日志记录
        List<RedisOperation> logList = redisService.getLogs(1000);
        //获取日志总数
        long logLen = redisService.getLogLen();
        model.addAttribute("infoList", ridList);
        model.addAttribute("logList", logList);
        model.addAttribute("logLen", logLen);
        return "redisMonitor";
    }

    //清空日志按钮
    @RequestMapping(value = "/logEmpty")
    @ResponseBody
    public String logEmpty() {
        return redisService.logEmpty();
    }

    //获取当前数据库中key的数量
    @RequestMapping(value = "/getKeysSize")
    @ResponseBody
    public String getKeysSize() {
        return JSON.toJSONString(redisService.getKeysSize());
    }

    //获取当前数据库内存使用大小情况
    @RequestMapping(value = "/getMemoryInfo")
    @ResponseBody
    public String getMemoryInfo() {
        return JSON.toJSONString(redisService.getMemoryInfo());
    }

}
