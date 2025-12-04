package top.chatzen.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.chatzen.model.AuthUserModel;
import top.chatzen.model.LoginCredentials;
import top.chatzen.model.Result;
import top.chatzen.service.IAuthService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IAuthService authService;

    /**
     * 用户注册
     * @param authUserModel 用户注册信息
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody AuthUserModel authUserModel) {
        try {
            // 添加用户
            authService.addUserFromAuthModel(authUserModel);

            return Result.succ("注册成功");
        } catch (Exception e) {
            return Result.fail(500, "注册失败: " + e.getMessage(), null);
        }
    }

    /**
     * 用户登录
     * @param credentials 用户登录凭据
     * @return 登录结果，包含JWT token
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginCredentials credentials) {
        String token = authService.login(credentials.getAccount(), credentials.getPassword());
        if (token != null) {
            return Result.succ(token);
        } else {
            return Result.fail(401, "用户名或密码错误", null);
        }
    }
}