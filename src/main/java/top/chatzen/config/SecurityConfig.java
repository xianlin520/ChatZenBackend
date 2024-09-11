package top.chatzen.config;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.CollectionUtils;
import top.chatzen.filter.JWTAuthenticationFilter;
import top.chatzen.handler.AuthenticationExceptionHandler;

import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Resource
    private AuthenticationExceptionHandler authenticationExceptionHandler;
    @Resource
    private JWTAuthenticationFilter tokenFilter;
    @Resource // 这个是当前项目Security模块的配置类
    private Config.Security security;
    
     // 放行接口
     @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         List<String> permitAllPaths = security.getPermitAllPath();
         // 配置不需要认证的请求(这里所有的路径可以写在配置文件上修改时就不用改代码)
         if (!CollectionUtils.isEmpty(permitAllPaths)) {
             permitAllPaths.forEach(path -> {
                 try {
                     // 放行白名单路径
                     http.authorizeHttpRequests(auth -> auth
                             .requestMatchers(path).permitAll());   // 放行白名单路径
                 } catch (Exception e) {
                     log.error("放行白名单路径失败", e);
                 }
             });
         }
         http    // 关闭csrf保护, 因为无需Session
                 .csrf(AbstractHttpConfigurer::disable)
                 // 其他所有请求都需要认证
                 .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                 // 不通过Session获取SecurityContext
                 .sessionManagement(session -> session
                         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 )// 配置异常处理
                 .exceptionHandling(exception -> exception
                         .authenticationEntryPoint(authenticationExceptionHandler)
                 );
         // 配置token过滤器
         http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
         return http.build();
     }
    
    // 配置密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * 身份认证管理器，调用authenticate()方法完成认证
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
}
