package vip.xianlin.utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

// 限流通用工具
@Component
public class FlowUtils {
    
    // 注入Redis服务
    @Resource
    StringRedisTemplate template;
    
    /**
     * 针对于单次频率限制，请求成功后，在冷却时间内不得再次进行请求
     *
     * @param key       键
     * @param blockTime 限制时间, 单位秒
     * @return 是否通过限流检查
     */
    public boolean limitOnceCheck(String key, int blockTime) {
        key = Const.VERIFY_EMAIL_LIMIT + key;
        // 判断是否存在, 存在则返回false
        if (Boolean.TRUE.equals(template.hasKey(key))) {
            return false;
        } else {
            // 设置过期时间, 限制时间内不能再次请求
            template.opsForValue().set(key, "1", blockTime, TimeUnit.SECONDS);
        }
        return true;
    }
    
    /**
     * 查询键的过期时间, 以秒为单位
     *
     * @param key 键
     * @return 过期时间, 单位秒
     */
    public long getExpireTime(String key) {
        key = Const.VERIFY_EMAIL_LIMIT + key;
        Long expire = template.opsForValue().getOperations().getExpire(key, TimeUnit.SECONDS);
        return expire == null ? 0 : expire;
    }
}
