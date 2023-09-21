package vip.xianlin.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vip.xianlin.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author yu
 * @date 2023/6/14 10:20
 * <p>
 * 权限过滤服务
 */
@Service
@Slf4j
public class MyUserDetailsServiceImpl implements UserDetailsService {
    
    /**
     * 自己的用户服务
     */
    // 注入用户服务
    @Resource
    private UserServiceImpl userService;
    
    
    /**
     * 通过重写 UserDetailsService 的 loadUserByUsername 方法实现用户认证和授权
     */
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        // 从数据库中查询用户信息
        UserEntity userEntity = userService.lambdaQuery().eq(UserEntity::getUserId, id).one();
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

