package top.chatzen.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.chatzen.entity.Result;
import top.chatzen.entity.UserAccount;
import top.chatzen.service.IUserAccountService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserAccountService userAccountService;
    
    @GetMapping("auth-test")
    public Result<String> authTest() {
        return Result.succ("auth-test");
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
