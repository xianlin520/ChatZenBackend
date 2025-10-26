package top.chatzen.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import top.chatzen.model.SecurityUser;

@Component
public class SecurityUtils {
    
    public static SecurityUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof SecurityUser) {
            return (SecurityUser) authentication.getPrincipal();
        }
        // 或者返回 Optional，更函数式
        // return Optional.ofNullable(authentication)
        //                 .map(Authentication::getPrincipal)
        //                 .filter(SecurityUser.class::isInstance)
        //                 .map(SecurityUser.class::cast);
        throw new IllegalStateException("当前未登录或用户信息异常");
    }
    
    /**
     * 次方法可以在Service层使用，获取当前用户ID
     *
     * @return 用户ID
     */
    public static Long getCurrentUserId() {
        return getCurrentUser().getUserId();
    }
}

