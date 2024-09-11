package top.chatzen.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.chatzen.entity.UserAccount;
import top.chatzen.service.IUserAccountService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Resource
    private IUserAccountService userAccountService;
    
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
