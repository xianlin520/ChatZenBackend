package vip.xianlin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import vip.xianlin.entity.UserEntity;
import vip.xianlin.mapper.UserMapper;
import vip.xianlin.service.IUserService;
import vip.xianlin.utils.Const;
import vip.xianlin.utils.FlowUtils;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author XianLin
 * @since 2023-09-18
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements IUserService {
    
    // 消息队列
    @Resource
    RabbitTemplate rabbitTemplate;
    // Redis服务
    @Resource
    StringRedisTemplate stringRedisTemplate;
    // 限流工具
    @Resource
    FlowUtils flow;
    
    /**
     * 注册邮箱验证码, 有效期5分钟
     * 60S内只能发送一次
     *
     * @param type  验证码类型
     * @param email 邮箱地址
     * @param ip    IP地址
     * @return 验证码
     */
    @Override
    public String registerEmailVerifyCode(String type, String email, String ip) {
        // 加锁, 防止并发
        synchronized (ip.intern()) {
            // 限流检查, 60秒内只能发送一次
            if (!flow.limitOnceCheck(ip, 60)) {
                log.warn("用户IP: {} 在60秒内重复请求注册邮箱: {} 验证码", ip, email);
                return "请勿频繁请求验证码, 剩余时间: " + flow.getExpireTime(ip);
            }
            // 生成数字验证码, 不低于四位
            Random random = new Random();
            int code = random.nextInt(8999) + 1000;
            // 发送消息到消息队列
            Map<String, Object> map = Map.of("type", type, "code", code, "email", email);
            rabbitTemplate.convertAndSend(Const.MQ_MAIL, map);
            // 将验证码存入Redis, 有效期5分钟
            stringRedisTemplate.opsForValue().set(Const.VERIFY_EMAIL_DATA + email, String.valueOf(code), 5, TimeUnit.MINUTES);
            return null;
        }
        
    }
    
}
