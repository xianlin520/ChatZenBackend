package top.chatzen.model;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import top.chatzen.entity.UserAccount;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class SecurityUserTest {

    @Test
    void testSecurityUserCreation() {
        // 准备测试数据
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1);
        userAccount.setUserId(1001L);
        userAccount.setAccount("testuser");
        userAccount.setPasswordHash("hashed_password");
        userAccount.setName("Test User");
        userAccount.setCreateTime(LocalDateTime.now());

        // 创建SecurityUser实例
        SecurityUser securityUser = new SecurityUser(userAccount);

        // 验证基本属性
        assertEquals(userAccount, securityUser.getUserAccount());
        assertEquals("testuser", securityUser.getUsername());
        assertEquals("hashed_password", securityUser.getPassword());
        assertEquals(Long.valueOf(1001L), securityUser.getUserId());
    }

    @Test
    void testUserDetailsMethods() {
        UserAccount userAccount = new UserAccount();
        userAccount.setAccount("testuser");
        userAccount.setPasswordHash("hashed_password");

        SecurityUser securityUser = new SecurityUser(userAccount);

        // 验证UserDetails接口方法
        assertTrue(securityUser.isAccountNonExpired());
        assertTrue(securityUser.isAccountNonLocked());
        assertTrue(securityUser.isCredentialsNonExpired());
        assertTrue(securityUser.isEnabled());

        // 验证权限（当前返回null，因为未实现）
        Collection authorities = securityUser.getAuthorities();
        assertNull(authorities);
    }
}