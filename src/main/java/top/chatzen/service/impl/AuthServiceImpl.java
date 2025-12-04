package top.chatzen.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import top.chatzen.constant.Const;
import top.chatzen.entity.UserAccount;
import top.chatzen.model.AuthUserModel;
import top.chatzen.model.RedisUserDto;
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
    @Resource
    private top.chatzen.service.IRoleService roleService;

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
        // 创建Redis存储的DTO对象，包含用户信息和JWT令牌
        RedisUserDto redisUserDto = RedisUserDto.fromSecurityUserAndToken(user, token);
        // 存入Redis - 直接存储对象，让RedisTemplate使用其配置的序列化器
        redisTemplate.opsForValue().set(
                String.format(Const.REDIS_KEY, userEntity.getId()), // 设置Redis Key - 常量
                redisUserDto,    // 存入Redis的值 - RedisUserDto封装对象
                jwtUtil.getEXPIRATION_TIME(),   // 设置数据过期时间 - jwt过期时间
                TimeUnit.MILLISECONDS); // 设置过期时间的单位 - ms
        return token;
    }
    
    @Override
    public void addUser(UserAccount userAccount) {
        // 对于新注册的用户，始终分配默认角色，忽略用户可能指定的角色
        userAccount.setRole(roleService.getDefaultRole());
        userAccountService.saveUserAccount(userAccount);
    }
    
    
    @Override
    public void addUserFromAuthModel(AuthUserModel authUserModel) {
        // 将AuthUserModel转换为UserAccount实体
        UserAccount userAccount = new UserAccount();
        userAccount.setName(authUserModel.getName());
        userAccount.setAccount(authUserModel.getAccount());
        userAccount.setPasswordHash(authUserModel.getPasswordHash());
        // 系统自动分配默认角色
        userAccount.setRole(roleService.getDefaultRole());
        userAccountService.saveUserAccount(userAccount);
    }

    @Override
    public void logout(String token) {
        // 从token中提取用户ID
        String userId = jwtUtil.extractUserId(token);
        // 从Redis中删除用户信息
        redisTemplate.delete(String.format(Const.REDIS_KEY, userId));
    }
}
