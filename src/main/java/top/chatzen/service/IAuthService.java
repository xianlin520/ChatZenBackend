package top.chatzen.service;

import top.chatzen.entity.UserAccount;

public interface IAuthService {
    String login(String username, String password);
    
    void addUser(UserAccount userAccount);
}
