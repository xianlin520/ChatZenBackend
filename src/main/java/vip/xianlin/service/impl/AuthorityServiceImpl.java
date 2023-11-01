package vip.xianlin.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vip.xianlin.entity.UserEntity;
import vip.xianlin.service.IAuthorityService;
import vip.xianlin.utils.Const;
import vip.xianlin.utils.FlowUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AuthorityServiceImpl implements IAuthorityService, UserDetailsService {
    
    // 消息队列
    @Resource
    RabbitTemplate rabbitTemplate;
    // Redis服务
    @Resource
    StringRedisTemplate stringRedisTemplate;
    // 限流工具
    @Resource
    FlowUtils flow;
    // 用户服务
    @Resource
    UserServiceImpl userService;
    
    @Override
    public long askEmailVerifyCode(String type, String email, String ip) {
        // 加锁, 防止并发
        synchronized (ip.intern()) {
            // 限流检查, 60秒内只能发送一次
            if (!flow.limitOnceCheck(ip, 60)) {
                log.warn("用户IP: {} 在60秒内重复请求注册邮箱: {} 验证码", ip, email);
                return flow.getExpireTime(ip);
            }
            // 生成数字验证码, 不低于四位
            Random random = new Random();
            int code = random.nextInt(8999) + 1000;
            // 发送消息到消息队列
            Map<String, Object> map = Map.of("type", type, "code", code, "email", email);
            rabbitTemplate.convertAndSend(Const.MQ_MAIL, map);
            // 将验证码存入Redis, 有效期5分钟
            stringRedisTemplate.opsForValue().set(Const.VERIFY_EMAIL_DATA + email, String.valueOf(code), 5, TimeUnit.MINUTES);
            return 0;
        }
        
    }
    
    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        // 从数据库中查询用户信息
        UserEntity userEntity = userService.lambdaQuery().eq(UserEntity::getUserId, userid).one();
        // 判空
        if (Objects.isNull(userEntity)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        // 获取用户权限列表
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        // 封装进去
        return new org.springframework.security.core.userdetails.User(
                userEntity.getUserName(),
                userEntity.getPassword(),
                // 账号是否可用
                true,
                // 账号过期
                true,
                // 密码过期
                true,
                // 账号锁定
                true,
                // 用户的权限列表
                authorities
        );
    }
}
