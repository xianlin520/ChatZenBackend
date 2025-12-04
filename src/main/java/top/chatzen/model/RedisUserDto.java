package top.chatzen.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.chatzen.entity.UserAccount;

/**
 * 专门用于Redis存储的用户数据传输对象
 * 专门用于存储用户认证信息和相关JWT令牌
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RedisUserDto {
    private UserAccount userAccount;
    private String jwtToken;

    /**
     * 从SecurityUser和JWT令牌创建RedisUserDto
     */
    public static RedisUserDto fromSecurityUserAndToken(SecurityUser securityUser, String jwtToken) {
        return new RedisUserDto(securityUser.getUserAccount(), jwtToken);
    }

    /**
     * 转换为SecurityUser
     */
    public SecurityUser toSecurityUser() {
        return new SecurityUser(this.userAccount);
    }
}