package top.chatzen.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthUserModel {
    /**
     * 用户昵称
     */
    private String name;
    
    /**
     * 用户账号-唯一
     */
    // 不能包含空格
    private String account;
    
    /**
     * 用户密码-加密
     */
    private String passwordHash;
}
