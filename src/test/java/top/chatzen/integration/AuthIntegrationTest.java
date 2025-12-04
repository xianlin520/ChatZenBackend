package top.chatzen.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "chat-zen.jwt.key=test-key",
    "chat-zen.jwt.expiration=3600000",
    "spring.data.redis.host=localhost",
    "spring.data.redis.port=6379"
})
class AuthIntegrationTest {

    @Test
    void contextLoads() {
        // 简单测试应用上下文是否能正确加载
        // 这可以验证配置类、Bean等是否正确配置
    }

    // 可以添加更多集成测试来测试整个认证流程
    // 例如测试用户登录、JWT生成和验证等端到端流程
}