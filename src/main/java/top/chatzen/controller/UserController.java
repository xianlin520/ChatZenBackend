package top.chatzen.controller;

import cn.hutool.core.lang.Snowflake;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.chatzen.entity.UserAccount;
import top.chatzen.model.AuthUserModel;
import top.chatzen.model.Result;
import top.chatzen.model.SecurityUser;
import top.chatzen.service.IAuthService;
import top.chatzen.service.IUserAccountService;
import top.chatzen.service.IUserStatusService;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserAccountService userAccountService;
    @Resource
    private IAuthService authService;
    @Resource
    private IUserStatusService userStatusService;
    @Resource
    private Snowflake snowflake;
    
    @PostMapping("/login")
    public Result<String> login(@RequestBody AuthUserModel authUserModel) {
        String username = authUserModel.getAccount();
        String password = authUserModel.getPasswordHash();
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return Result.fail(500, "用户名或密码为空！", null);
        }
        String token = authService.login(username, password);
        return Result.succ(token);
    }
    
    @PostMapping("/register")
    public Result<String> register(@RequestBody AuthUserModel authUserModel) {
        // 判断account与passwordHash是否为空
        if (authUserModel.getAccount() == null || authUserModel.getPasswordHash() == null) {
            return Result.fail("用户名或密码为空");
        }
        // 检查是否包含空格
        if (authUserModel.getAccount().contains(" ") || authUserModel.getPasswordHash().contains(" ")) {
            return Result.fail("用户名或密码不能包含空格");
        }
        // 生成雪花ID, 作为user_id
        long userId = snowflake.nextId();
        
        // 新建UserAccount对象, 将authUserModel中的数据复制到UserAccount中
        UserAccount userAccount = new UserAccount();
        userAccount.setAccount(authUserModel.getAccount());
        userAccount.setPasswordHash(authUserModel.getPasswordHash());
        userAccount.setName(authUserModel.getName());
        userAccount.setUserId(userId);
        boolean save = userAccountService.save(userAccount);
        if (!save) return Result.fail("用户添加失败");
        boolean b = userStatusService.addUserStatusById(userId);
        if (!b) return Result.fail("用户状态添加失败");
        return Result.succ("用户添加成功");
        
    }
    
    @GetMapping("auth-test")
    public Result<SecurityUser> authTest() {
        // 从SecurityContextHolder获取Authentication对象, 然后获取SecurityUser封装用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        // 获取用户信息, 直接返回用户实体类型会将所有信息都返回, 包括账号密码
        return Result.succ(user);
    }
}
