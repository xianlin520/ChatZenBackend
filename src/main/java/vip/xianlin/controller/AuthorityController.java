package vip.xianlin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.xianlin.entity.Result;
import vip.xianlin.entity.UserEntity;
import vip.xianlin.entity.vo.request.UserAuthVo;
import vip.xianlin.entity.vo.response.UserAuthTokenVo;
import vip.xianlin.service.IAuthorityService;
import vip.xianlin.service.IUserService;
import vip.xianlin.utils.JwtUtils;

import java.util.Collection;

@RestController
@RequestMapping("auth")
@Slf4j
@Validated
@Tag(name = "用户认证", description = "用户登录、注册等操作")
public class AuthorityController {
    
    /**
     * 用户表操作
     */
    @Resource
    private IUserService userService;
    /**
     * 鉴权服务
     */
    @Resource
    private IAuthorityService authorityService;
    /**
     * 登录认证操作
     */
    @Resource
    private AuthenticationManager authenticationManager;
    
    @Resource
    private JwtUtils jwtUtils;
    
    // TODO 用户注册接口及业务代码实现
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "用户注册, 传入用户信息")
    public Result register() {
        return Result.succ("注册成功");
    }
    
    // TODO 用户删除账号接口及业务代码实现
    @DeleteMapping("/delete")
    @Operation(summary = "用户删除", description = "用户删除账号, 传入用户信息")
    public Result delete() {
        return Result.succ("删除成功");
    }
    
    @GetMapping("/ask-email-code")
    @Operation(summary = "请求邮箱验证码", description = "传入邮箱地址和验证码类型, 发送验证码到邮箱")
    public Result askEmailCode(@RequestParam @Email String email, // 邮箱地址, 必须符合邮箱格式
                               @RequestParam @NotNull String type,
                               HttpServletRequest request) {
        long data = authorityService.askEmailVerifyCode(type, email, request.getRemoteAddr());
        if (data == 0)
            return Result.succ("验证码发送成功");
        else
            return Result.fail(400, "请勿频繁请求验证码", data);
    }
    
    
    // TODO 发送手机验证码接口及业务代码实现
    @GetMapping("/ask-phone-code")
    @Operation(summary = "请求手机验证码", description = "传入手机号和验证码类型, 发送验证码到手机")
    public Result askPhoneCode(@RequestParam @NotNull String phone, // 手机号, 必须符合手机号格式
                               @RequestParam @NotNull String type,
                               HttpServletRequest request) {
        return Result.succ("验证码发送成功");
    }
    
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录认证")
    public Result login(@RequestBody UserAuthVo user) {
        // TODO 实现用户密码登录, 邮箱验证码登录, 手机验证码登录业务代码
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
