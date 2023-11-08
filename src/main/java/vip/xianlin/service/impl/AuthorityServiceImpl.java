package vip.xianlin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import vip.xianlin.utils.DataEnum;
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
    public UserEntity getUserByPrincipal(String principal) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserEntity::getEmail, principal).or().eq(UserEntity::getPhone, principal).or().eq(UserEntity::getUserId, principal);
        return userService.getOne(wrapper);
        
    }
    
    @Override
    public boolean verifyCode(String principal, String verifyCode) {
        // 从Redis中获取邮箱验证码
        String emailCode = stringRedisTemplate.opsForValue().get(Const.VERIFY_EMAIL_DATA + principal);
        // 从Redis中获取手机验证码
        String phoneCode = stringRedisTemplate.opsForValue().get(Const.VERIFY_PHONE_DATA + principal);
        // 判断验证码是否正确
        if (Objects.equals(verifyCode, emailCode) || Objects.equals(verifyCode, phoneCode)) {
            // 验证码正确, 删除Redis中的验证码
            stringRedisTemplate.delete(Const.VERIFY_EMAIL_DATA + principal);
            stringRedisTemplate.delete(Const.VERIFY_PHONE_DATA + principal);
            return true;
        }
        return false;
    }
    
    @Override
    public long askEmailVerifyCode(DataEnum.EmailType type, String email, String ip) {
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
            Map<String, Object> map = Map.of("type", type.name(), "code", code, "email", email);
            rabbitTemplate.convertAndSend(Const.MQ_MAIL, map);
            // 将验证码存入Redis, 有效期5分钟
            stringRedisTemplate.opsForValue().set(Const.VERIFY_EMAIL_DATA + email, String.valueOf(code), 5, TimeUnit.MINUTES);
            return 0;
        }
    }
    
    @Override
    public UserDetails loadUserByUsername(String principal) throws UsernameNotFoundException {
        // 从数据库中查询用户信息, 最多一条
        UserEntity userEntity = getUserByPrincipal(principal);
        
        // 账号是否可用
        boolean enabled = true;
        // 账号是否锁定
        boolean noLocked = true;
        // 判空
        if (Objects.isNull(userEntity))
            enabled = false;
        
        // 获取用户权限列表
        List<GrantedAuthority> authorities = new ArrayList<>();
        String[] roles = userEntity.getRoles();
        // 遍历, 将每个角色添加到权限列表中
        for (String r : roles) {
            if (r.equals(Const.Role.BAN.name())) {
                noLocked = false;
                break;
            }
            authorities.add(new SimpleGrantedAuthority(r));
        }
        
        // 封装进去
        return new org.springframework.security.core.userdetails.User(
                userEntity.getUserName(),
                userEntity.getPassword(),
                // 账号是否可用
                enabled,
                // 账号过期
                true,
                // 密码过期
                true,
                // 账号锁定
                noLocked,
                // 用户的权限列表
                authorities
        );
    }
}
