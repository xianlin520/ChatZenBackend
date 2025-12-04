package top.chatzen.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.chatzen.model.Result;
import top.chatzen.service.IAuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private IAuthService authService;

    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        // 从请求头获取Authorization信息
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            // 调用登出服务，将用户信息从Redis中删除
            authService.logout(token);
            return Result.succ("登出成功");
        } else {
            return Result.fail(400, "无效的token", null);
        }
    }
}