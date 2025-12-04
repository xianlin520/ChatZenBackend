package top.chatzen.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.TestPropertySource;
import jakarta.annotation.Resource;
import top.chatzen.model.RedisUserDto;
import top.chatzen.entity.UserAccount;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.data.redis.host=localhost",
    "spring.data.redis.port=6379"
})
class RedisSerializationTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void testRedisUserDtoSerialization() {
        // 准备测试数据
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1);
        userAccount.setUserId(1001L);
        userAccount.setAccount("testuser");
        userAccount.setPasswordHash("hashed_password");
        userAccount.setName("Test User");
        userAccount.setCreateTime(LocalDateTime.now());

        String jwtToken = "test-jwt-token";

        RedisUserDto originalDto = new RedisUserDto(userAccount, jwtToken);

        // 存储到Redis
        String key = "test:user:1001";
        redisTemplate.opsForValue().set(key, originalDto, 600, TimeUnit.SECONDS);

        // 从Redis获取
        Object retrievedObj = redisTemplate.opsForValue().get(key);
        assertNotNull(retrievedObj);
        assertTrue(retrievedObj instanceof RedisUserDto);

        RedisUserDto retrievedDto = (RedisUserDto) retrievedObj;

        // 验证数据完整性
        assertEquals(originalDto.getUserAccount().getAccount(), retrievedDto.getUserAccount().getAccount());
        assertEquals(originalDto.getUserAccount().getPasswordHash(), retrievedDto.getUserAccount().getPasswordHash());
        assertEquals(originalDto.getUserAccount().getName(), retrievedDto.getUserAccount().getName());
        assertEquals(originalDto.getJwtToken(), retrievedDto.getJwtToken());
        assertEquals(originalDto.getUserAccount().getUserId(), retrievedDto.getUserAccount().getUserId());
    }
}