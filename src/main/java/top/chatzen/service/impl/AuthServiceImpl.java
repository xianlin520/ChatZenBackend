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
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (null == authenticate) {
            log.error("认证失败 {username:{}, password:{}}", username, password);
            return null;
        }
        SecurityUser user = (SecurityUser) authenticate.getPrincipal();
        UserAccount userEntity = user.getUserAccount();
        // 生成token
        String token = jwtUtil.generateToken(userEntity.getId().toString());
        // 存入Redis
        redisTemplate.opsForValue().set(String.format(Const.REDIS_KEY, userEntity.getId()),
                JSON.toJSONString(user), jwtUtil.getEXPIRATION_TIME(), TimeUnit.MILLISECONDS);
        return token;
        
    
    }
    
    @Override
    public void addUser(UserAccount userAccount) {
        userAccountService.saveUserAccount(userAccount);
    }
}
