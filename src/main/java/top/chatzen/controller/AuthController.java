package top.chatzen.controller;

import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.chatzen.entity.Result;
import top.chatzen.service.IAuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Resource
    private IAuthService authService;
    
    @PostMapping("/login")
    public Result<String> login(@RequestBody JSONObject params) {
        String username = params.getString("username");
        String password = params.getString("password");
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return Result.fail(500, "用户名或密码为空！", null);
        }
        String token = authService.login(username, password);
        return Result.succ(token);
    }
}
