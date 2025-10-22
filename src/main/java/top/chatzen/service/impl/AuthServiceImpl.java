package top.chatzen.service.impl;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import top.chatzen.constant.Const;
import top.chatzen.entity.UserAccount;
import top.chatzen.model.SecurityUser;
import top.chatzen.service.IAuthService;
import top.chatzen.service.IUserAccountService;
import top.chatzen.util.JwtUtil;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class AuthServiceImpl implements IAuthService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private IUserAccountService userAccountService;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    
    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        // 调用AuthenticationManager的authenticate方法进行认证, 该方法会调用UserDetailsService的loadUserByUsername方法进行认证
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 认证完成后会返回Authentication对象, 该对象中包含了认证成功的用户信息
        // 如果认证失败, 则authenticate方法会抛出AuthenticationException异常, 我已经配置了处理异常的类
        if (null == authenticate) {
            log.error("认证失败 {username:{}, password:{}}", username, password);
            return null;
        }
        // 从认证对象中获取用户信息
        SecurityUser user = (SecurityUser) authenticate.getPrincipal();
        UserAccount userEntity = user.getUserAccount();
        // 生成token
        String token = jwtUtil.generateToken(String.valueOf(userEntity.getId()));
        // 将Token存入SecurityUser
        user.setJwtToken(token);
        // 存入Redis
        redisTemplate.opsForValue().set(
                String.format(Const.REDIS_KEY, userEntity.getId()), // 设置Redis Key - 常量
                JSON.toJSONString(user),    // 存入Redis的值 - SecurityUser封装对象
                jwtUtil.getEXPIRATION_TIME(),   // 设置数据过期时间 - jwt过期时间
                TimeUnit.MILLISECONDS); // 设置过期时间的单位 - ms
        return token;
    }
    
    @Override
    public void addUser(UserAccount userAccount) {
        userAccountService.saveUserAccount(userAccount);
    }
}
