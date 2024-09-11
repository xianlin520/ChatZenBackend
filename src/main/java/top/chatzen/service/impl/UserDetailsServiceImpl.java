package top.chatzen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.chatzen.entity.UserAccount;
import top.chatzen.model.SecurityUser;
import top.chatzen.service.IUserAccountService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Resource
    private IUserAccountService userAccountService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 通过MybatisPlus查询用户信息
        QueryWrapper<UserAccount> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserAccount::getUsername, username);
        UserAccount userAccount = userAccountService.getOne(wrapper);
        
        if (userAccount == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 返回封装UserDetails对象
        return new SecurityUser(userAccount);
    }
}
