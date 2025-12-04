package top.chatzen.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        // 使用测试密钥
        ReflectionTestUtils.setField(jwtUtil, "SECRET_KEY", "test-secret-key");
        ReflectionTestUtils.setField(jwtUtil, "EXPIRATION_TIME", 3600000L); // 1小时
        
        // 初始化algorithm
        Algorithm algorithm = Algorithm.HMAC256("test-secret-key");
        ReflectionTestUtils.setField(jwtUtil, "algorithm", algorithm);
    }

    @Test
    void testGenerateToken() {
        String userId = "123";
        String token = jwtUtil.generateToken(userId);

        // 验证token不为空
        assertNotNull(token);
        assertFalse(token.isEmpty());

        // 验证token可以被解析
        String decodedUserId = jwtUtil.extractUserId(token);
        assertEquals(userId, decodedUserId);
    }

    @Test
    void testExtractUserId() {
        String userId = "456";
        String token = jwtUtil.generateToken(userId);

        String extractedUserId = jwtUtil.extractUserId(token);
        assertEquals(userId, extractedUserId);
    }

    @Test
    void testIsTokenExpired() throws InterruptedException {
        // 通过设置一个过去的时间来创建已过期的令牌
        String expiredToken = JWT.create()
                .withSubject("789")
                .withIssuedAt(new Date(System.currentTimeMillis() - 200)) // 200ms前签发
                .withExpiresAt(new Date(System.currentTimeMillis() - 100)) // 100ms前过期
                .sign(Algorithm.HMAC256("test-secret-key"));

        boolean isExpired = jwtUtil.isTokenExpired(expiredToken);
        assertTrue(isExpired);
    }

    @Test
    void testTokenNotExpired() {
        String userId = "999";
        String token = jwtUtil.generateToken(userId);

        boolean isExpired = jwtUtil.isTokenExpired(token);
        assertFalse(isExpired);
    }
}