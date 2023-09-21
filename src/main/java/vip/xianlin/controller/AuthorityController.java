package vip.xianlin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vip.xianlin.entity.Result;
import vip.xianlin.entity.UserEntity;
import vip.xianlin.entity.vo.request.UserAuthVo;
import vip.xianlin.entity.vo.response.UserAuthTokenVo;
import vip.xianlin.service.impl.UserServiceImpl;
import vip.xianlin.utils.JwtUtils;

import java.util.Collection;

@RestController
@RequestMapping("auth")
@Slf4j
@Tag(name = "用户认证", description = "用户登录、注册等操作")
public class AuthorityController {
    
    /**
     * 用户表操作
     */
    @Resource
    private UserServiceImpl userService;
    /**
     * 登录认证操作
     */
    @Resource
    private AuthenticationManager authenticationManager;
    
    @Resource
    private JwtUtils jwtUtils;
    
    
    @GetMapping("/ask-email-code")
    @Operation(summary = "请求邮箱验证码", description = "传入邮箱地址和验证码类型, 发送验证码到邮箱")
    public Result askEmailCode(@RequestParam String email, @RequestParam String type, HttpServletRequest request) {
        String msg = userService.registerEmailVerifyCode(type, email, request.getRemoteAddr());
        if (msg == null)
            return Result.succ("验证码发送成功");
        else
            return Result.fail(msg);
    }
    
    
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录认证")
    public Result login(@RequestBody UserAuthVo user) {
        // 创建用户名密码验证令牌
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword());
        try {
            // 进行认证
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            // 获取用户角色对象
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            // 将认证信息保存在会话中
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // 获取用户数据
            UserEntity one = userService.lambdaQuery().eq(UserEntity::getUserId, user.getUserId()).one();
            // 创建用户令牌对象
            UserAuthTokenVo userAuthTokenVo = new UserAuthTokenVo();
            // 把两个对象进行拷贝
            BeanUtils.copyProperties(one, userAuthTokenVo);
            // 设置用户令牌
            userAuthTokenVo.setToken(jwtUtils.createJwt(authorities, one.getUserId()));
            // 认证成功响应
            return Result.succ(userAuthTokenVo);
        } catch (AuthenticationException e) {
            // 认证失败响应
            return Result.fail("认证失败");
        }
    }
}
