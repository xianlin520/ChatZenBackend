package top.chatzen.controller;

import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import top.chatzen.model.Result;

/**
 * Test - 测试接口
 */
@Slf4j
@RestController
@RequestMapping("/http-test")
public class TestController {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    
    /**
     * 测试Redis连接和基本操作
     * 
     * @return Redis中存储的测试值
     */
    @GetMapping("/test-redis")
    public Result<Object> testRedis() {
        redisTemplate.opsForValue().set("test", "test");
        return Result.succ(redisTemplate.opsForValue().get("test"));
    }
    
    /**
     * 简单的GET请求测试接口
     * 
     * @return 固定返回"hello"字符串
     */
    @GetMapping("/hello-get")
    public Result<String> hello() {
        log.info("/hello-get - 收到请求");
        return Result.succ("hello");
    }
    
    /**
     * 带路径参数的GET请求测试接口
     * 
     * @param name 从URL路径中提取的名称参数
     * @return 包含名称的问候语
     */
    @GetMapping("/hello/{name}")
    public Result<String> hello(@PathVariable String name) {
        log.info("/hello/{data} - 收到请求");
        return Result.succ("hello " + name);
    }
    
    /**
     * POST请求测试接口，从请求体中获取参数
     * 
     * @param json 请求体中的JSON对象，应包含name字段
     * @return 包含name字段值的问候语
     */
    @PostMapping("/hello-post")
    public Result<String> helloPost(@RequestBody JSONObject json) {
        log.info("/hello-post - 收到请求");
        log.info("请求体: {}", json);
        return Result.succ("hello " + json.getString("name"));
    }
    
}