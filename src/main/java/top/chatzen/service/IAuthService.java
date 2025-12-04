package top.chatzen.service;

import top.chatzen.entity.UserAccount;
import top.chatzen.model.AuthUserModel;

public interface IAuthService {
    String login(String username, String password);

    void addUser(UserAccount userAccount);

    void addUserFromAuthModel(AuthUserModel authUserModel);

    void logout(String token);
}
