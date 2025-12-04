package top.chatzen.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户登录凭据
 */
@Getter
@Setter
public class LoginCredentials {
    private String account;
    private String password;
}