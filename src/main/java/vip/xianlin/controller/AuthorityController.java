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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.xianlin.entity.Result;
import vip.xianlin.entity.UserEntity;
import vip.xianlin.entity.vo.request.UserAuthVo;
import vip.xianlin.entity.vo.request.UserCodeAuthVo;
import vip.xianlin.entity.vo.request.UserRegisterVo;
import vip.xianlin.entity.vo.response.UserAuthTokenVo;
import vip.xianlin.entity.vo.response.UserBasicInfoVo;
import vip.xianlin.service.IAuthorityService;
import vip.xianlin.service.IUserService;
import vip.xianlin.utils.Const;
import vip.xianlin.utils.JwtUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
    
    // 密码加密器
    @Resource
    private PasswordEncoder passwordEncoder;
    
    @Resource
    private JwtUtils jwtUtils;
    
    
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "用户注册, 传入用户信息")
    public Result register(@RequestBody UserRegisterVo userRegisterVo,
                           HttpServletRequest request) {
        // 打印用户注册信息
        log.info("用户注册邮箱: {}", userRegisterVo.getEmail());
        // 判断用户是否已经注册
        if (authorityService.getUserByPrincipal(userRegisterVo.getEmail()) != null) {
            return Result.fail("用户已经注册");
        }
        // 判断邮箱与验证码是否正确
        if (!authorityService.verifyCode(userRegisterVo.getEmail(), userRegisterVo.getEmailCode())) {
            return Result.fail("验证码错误");
        }
        // 创建用户对象
        UserEntity user = new UserEntity();
        // 把用户信息设置到用户对象中
        user.setEmail(userRegisterVo.getEmail());
        user.setUserName(userRegisterVo.getNickname());
        user.setPassword(passwordEncoder.encode(userRegisterVo.getPassword()));
        user.setHeadThumb("url");
        // 保存用户信息
        userService.save(user);
        UserEntity userByPrincipal = authorityService.getUserByPrincipal(userRegisterVo.getEmail());
        // 拷贝用户信息到基本信息对象中
        UserBasicInfoVo userBasicInfoVo = new UserBasicInfoVo();
        BeanUtils.copyProperties(userByPrincipal, userBasicInfoVo);
        // 返回用户基本信息
        return Result.succ(userBasicInfoVo);
    }
    
    // TODO 用户删除账号接口及业务代码实现
    @DeleteMapping("/delete")
    @Operation(summary = "用户删除", description = "用户删除账号, 传入用户信息")
    public Result delete() {
        return Result.succ("删除接口-开发中");
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
        return Result.succ("手机验证码-开发中");
    }
    
    @PostMapping("/login-by-code")
    @Operation(summary = "验证码登录", description = "传入验证码和验证码类型, 进行登录认证")
    public Result loginByCode(@RequestBody UserCodeAuthVo userCodeAuthVo,
                              HttpServletRequest request) {
        String principal = null;
        // 判断邮箱是否为空
        if (Objects.nonNull(userCodeAuthVo.getPrincipal())) {
            // 邮箱不为空, 则获取邮箱地址
            principal = userCodeAuthVo.getPrincipal();
        } else {
            // 邮箱和手机号都为空, 则返回错误信息
            return Result.fail("邮箱和手机号不能同时为空");
        }
        // 验证验证码是否正确
        if (!authorityService.verifyCode(principal, userCodeAuthVo.getAuthCode())) {
            return Result.fail("验证码错误");
        }
        
        // 获取用户信息
        UserEntity user = authorityService.getUserByPrincipal(principal);
        // 获取用户权限列表
        List<GrantedAuthority> authorities = new ArrayList<>();
        String[] roles = user.getRoles();
        // 遍历, 将每个角色添加到权限列表中
        for (String r : roles) {
            if (r.equals(Const.Role.BAN.name())) {
                // 如果用户被封禁, 则返回错误信息
                return Result.fail("用户被封禁");
            }
            authorities.add(new SimpleGrantedAuthority(r));
        }
        
        // 创建用户令牌对象
        UserAuthTokenVo userAuthTokenVo = new UserAuthTokenVo();
        // 把两个对象进行拷贝
        BeanUtils.copyProperties(user, userAuthTokenVo);
        // 设置用户令牌
        userAuthTokenVo.setToken(jwtUtils.createJwt(authorities, user.getUserId(), userCodeAuthVo.isRememberMe()));
        // 认证成功响应
        return Result.succ(userAuthTokenVo);
    }
    
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录认证")
    public Result login(@RequestBody UserAuthVo user) {
        // 创建用户名密码验证令牌UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getPrincipal(), user.getPassword());
        try {
            // 进行认证
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            // 获取用户角色对象
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            // 获取用户数据
            UserEntity one = authorityService.getUserByPrincipal(user.getPrincipal());
            // 创建用户令牌对象
            UserAuthTokenVo userAuthTokenVo = new UserAuthTokenVo();
            // 把两个对象进行拷贝
            BeanUtils.copyProperties(one, userAuthTokenVo);
            // 设置用户令牌
            userAuthTokenVo.setToken(jwtUtils.createJwt(authorities, one.getUserId(), user.isRememberMe()));
            // 认证成功响应
            return Result.succ(userAuthTokenVo);
        } catch (AuthenticationException e) {
            // 认证失败响应
            return Result.fail("认证失败");
        }
    }
}
