package top.chatzen.model;

import org.junit.jupiter.api.Test;
import top.chatzen.entity.UserAccount;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RedisUserDtoTest {

    @Test
    void testRedisUserDtoCreation() {
        // 准备测试数据
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1);
        userAccount.setUserId(1001L);
        userAccount.setAccount("testuser");
        userAccount.setPasswordHash("hashed_password");
        userAccount.setName("Test User");
        userAccount.setCreateTime(LocalDateTime.now());

        String jwtToken = "test-jwt-token";

        // 测试有参构造函数
        RedisUserDto dto = new RedisUserDto(userAccount, jwtToken);

        // 验证属性
        assertEquals(userAccount, dto.getUserAccount());
        assertEquals(jwtToken, dto.getJwtToken());
    }

    @Test
    void testRedisUserDtoNoArgsConstructor() {
        // 测试无参构造函数
        RedisUserDto dto = new RedisUserDto();

        // 验证默认值
        assertNull(dto.getUserAccount());
        assertNull(dto.getJwtToken());

        // 测试setter方法
        UserAccount userAccount = new UserAccount();
        userAccount.setAccount("testuser");
        String jwtToken = "test-jwt-token";

        dto.setUserAccount(userAccount);
        dto.setJwtToken(jwtToken);

        assertEquals(userAccount, dto.getUserAccount());
        assertEquals(jwtToken, dto.getJwtToken());
    }

    @Test
    void testFromSecurityUserAndToken() {
        // 准备测试数据
        UserAccount userAccount = new UserAccount();
        userAccount.setAccount("testuser");
        userAccount.setPasswordHash("hashed_password");
        SecurityUser securityUser = new SecurityUser(userAccount);
        String jwtToken = "test-jwt-token";

        // 测试fromSecurityUserAndToken方法
        RedisUserDto dto = RedisUserDto.fromSecurityUserAndToken(securityUser, jwtToken);

        // 验证结果
        assertEquals(userAccount, dto.getUserAccount());
        assertEquals(jwtToken, dto.getJwtToken());
    }

    @Test
    void testToSecurityUser() {
        // 准备测试数据
        UserAccount userAccount = new UserAccount();
        userAccount.setAccount("testuser");
        userAccount.setPasswordHash("hashed_password");
        String jwtToken = "test-jwt-token";

        RedisUserDto dto = new RedisUserDto(userAccount, jwtToken);

        // 测试toSecurityUser方法
        SecurityUser securityUser = dto.toSecurityUser();

        // 验证结果
        assertEquals(userAccount, securityUser.getUserAccount());
        assertEquals("testuser", securityUser.getUsername());
        assertEquals("hashed_password", securityUser.getPassword());
    }
}