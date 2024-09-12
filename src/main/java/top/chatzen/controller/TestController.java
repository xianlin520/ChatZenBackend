package top.chatzen.controller;

import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import top.chatzen.entity.Result;

@Slf4j
@RestController
@RequestMapping("/http-test")
public class TestController {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    
    @GetMapping("/test-redis")
    public Result<Object> testRedis() {
        redisTemplate.opsForValue().set("test", "test");
        return Result.succ(redisTemplate.opsForValue().get("test"));
    }
    
    @GetMapping("/hello-get")
    public Result<String> hello() {
        log.info("/hello-get - 收到请求");
        return Result.succ("hello");
    }
    
    // 路径传参测试
    @GetMapping("/hello/{name}")
    public Result<String> hello(@PathVariable String name) {
        log.info("/hello/{data} - 收到请求");
        return Result.succ("hello " + name);
    }
    
    // POST Body传参测试
    @PostMapping("/hello-post")
    public Result<String> helloPost(@RequestBody JSONObject json) {
        log.info("/hello-post - 收到请求");
        log.info("请求体: {}", json);
        return Result.succ("hello " + json.getString("name"));
    }
    
}
