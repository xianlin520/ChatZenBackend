package top.chatzen.controller;

import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import top.chatzen.model.Result;
import top.chatzen.entity.UserAccount;
import top.chatzen.model.SecurityUser;
import top.chatzen.service.IUserAccountService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserAccountService userAccountService;
    
    @GetMapping("auth-test")
    public Result<SecurityUser> authTest() {
        // 从SecurityContextHolder获取Authentication对象, 然后获取SecurityUser封装用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        // 获取用户信息, 直接返回用户实体类型会将所有信息都返回, 包括账号密码
        return Result.succ(user);
    }
    
    @PostMapping("/add")
    public String addUser(@RequestBody UserAccount userAccount) {
        boolean save = userAccountService.save(userAccount);
        if (save) {
            return "User added successfully";
        } else {
            return "Failed to add user";
        }
    }
}
