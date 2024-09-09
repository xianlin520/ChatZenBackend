package top.chatzen.chatzenspring.controller;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.web.bind.annotation.*;
import top.chatzen.chatzenspring.entity.Result;

@RestController
@RequestMapping("/http-test")
public class TestController {
    
    @GetMapping("/hello-get")
    public Result<String> hello() {
        return Result.succ("hello");
    }
    
    // 路径传参测试
    @GetMapping("/hello/{name}")
    public Result<String> hello(@PathVariable String name) {
        return Result.succ("hello " + name);
    }
    
    // POST Body传参测试
    @PostMapping("/hello-post")
    public Result<String> helloPost(@RequestBody JSONObject json) {
        return Result.succ("hello " + json.getString("name"));
    }
  
}
