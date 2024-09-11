package top.chatzen.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import top.chatzen.entity.UserAccount;

import java.util.Collection;

public class SecurityUser implements UserDetails {
    private UserAccount userAccount;
    
    public SecurityUser(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
    
    public UserAccount getUserAccount() {
        return userAccount;
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
        return null;
    }
    
    @Override
    public String getPassword() {
        return userAccount.getPasswordHash();
    }
    
    @Override
    public String getUsername() {
        return userAccount.getUsername();
    }
}
