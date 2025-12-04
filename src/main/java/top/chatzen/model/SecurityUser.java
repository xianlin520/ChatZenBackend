package top.chatzen.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import top.chatzen.entity.UserAccount;

import java.util.Collection;

@Getter
public class SecurityUser implements UserDetails {
    private final UserAccount userAccount;

    public SecurityUser(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        // false 用户帐号已过期
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        // false 用户帐号被锁定
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        // false 凭证已过期
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        // false 用户帐号已禁用
        return true;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 如果用户账户中有角色信息，则返回对应权限，否则返回默认USER权限
        String userRole = userAccount.getRole();
        if (userRole != null && !userRole.isEmpty()) {
            // 确保角色名称符合Spring Security规范（以ROLE_为前缀）
            String role = userRole.toUpperCase(); // 统一转换为大写
            return java.util.Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
        } else {
            // 默认角色为USER
            return java.util.Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }
    
    @Override
    public String getPassword() {
        return userAccount.getPasswordHash();
    }
    
    @Override
    public String getUsername() {
        return userAccount.getAccount();
    }
    
    public Long getUserId() {
        return userAccount.getUserId();
    }
}
