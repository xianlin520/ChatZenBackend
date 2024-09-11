package top.chatzen;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.chatzen.util.JwtUtil;

@Slf4j
@SpringBootTest
public class TestUtil {
    
    @Resource
    JwtUtil jwtUtil;
    
    @Test
    void testJwtUtil() {
        
        log.info("测试 - JwtUtil");
        
        // 生成Token
        String token = jwtUtil.generateToken("123456");
        log.info("测试生成token: {}", token);
        
        // 检查Token是否过期
        boolean isTokenExpired = jwtUtil.isTokenExpired(token);
        log.info("验证Token是否过期: {}", isTokenExpired);
        
        // 提取用户ID
        String userId = jwtUtil.extractUserId(token);
        log.info("提取userId: {}", userId);
        
        log.info("测试结束 - JwtUtil");
    }
    
}
