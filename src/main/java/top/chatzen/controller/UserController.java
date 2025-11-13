package top.chatzen.controller;

import cn.hutool.core.lang.Snowflake;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.chatzen.entity.UserAccount;
import top.chatzen.model.AuthUserModel;
import top.chatzen.model.Result;
import top.chatzen.model.SecurityUser;
import top.chatzen.service.IAuthService;
import top.chatzen.service.IUserAccountService;
import top.chatzen.service.IUserStatusService;

/**
 * User - 用户管理
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserAccountService userAccountService;
    @Resource
    private IAuthService authService;
    @Resource
    private IUserStatusService userStatusService;
    @Resource
    private Snowflake snowflake;
    
    /**
     * 用户登录接口
     * 验证用户凭证并生成JWT令牌
     * 
     * @param authUserModel 用户认证信息模型
     *                      account: 用户账号
     *                      passwordHash: 用户密码（已加密）
     * @return 登录结果，成功时返回JWT令牌
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody AuthUserModel authUserModel) {
        String username = authUserModel.getAccount();
        String password = authUserModel.getPasswordHash();
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return Result.fail(500, "用户名或密码为空！", null);
        }
        String token = authService.login(username, password);
        return Result.succ(token);
    }
    
    /**
     * 用户注册接口
     * 创建新用户账户并初始化用户状态
     * 
     * @param authUserModel 用户注册信息模型
     *                      account: 用户账号（需唯一）
     *                      passwordHash: 用户密码（已加密）
     *                      name: 用户昵称（可选）
     * @return 注册结果
     */
    @PostMapping("/register")
    @Transactional
    public Result<String> register(@RequestBody AuthUserModel authUserModel) {
        // 统一使用StringUtils.hasText()进行空值检查
        if (!StringUtils.hasText(authUserModel.getAccount()) || !StringUtils.hasText(authUserModel.getPasswordHash())) {
            return Result.fail("用户名或密码为空");
        }
        
        // 检查用户名长度（3-20个字符）
        String account = authUserModel.getAccount();
        if (account.length() < 3 || account.length() > 20) {
            return Result.fail("用户名长度必须在3-20个字符之间");
        }
        
        // 检查密码长度（6-50个字符）
        String password = authUserModel.getPasswordHash();
        if (password.length() < 6 || password.length() > 50) {
            return Result.fail("密码长度必须在6-50个字符之间");
        }
        
        // 检查是否包含空格或其他空白字符
        if (containsWhitespace(account) || containsWhitespace(password)) {
            return Result.fail("用户名或密码不能包含空格或空白字符");
        }
        
        // 生成雪花ID, 作为user_id
        long userId = snowflake.nextId();
        
        // 新建UserAccount对象, 将authUserModel中的数据复制到UserAccount中
        UserAccount userAccount = new UserAccount();
        userAccount.setAccount(authUserModel.getAccount());
        userAccount.setPasswordHash(authUserModel.getPasswordHash());
        userAccount.setName(authUserModel.getName());
        userAccount.setUserId(userId);
        
        try {
            boolean save = userAccountService.save(userAccount);
            if (!save) return Result.fail("用户添加失败");
            
            boolean b = userStatusService.addUserStatusById(userId);
            if (!b) return Result.fail("用户状态添加失败");
            
            return Result.succ("用户添加成功");
        } catch (Exception e) {
            log.error("用户注册失败", e);
            return Result.fail("用户注册失败: " + e.getMessage());
        }
    }
    
    /**
     * 检查字符串是否包含空白字符
     * 
     * @param str 要检查的字符串
     * @return 如果包含空白字符返回true，否则返回false
     */
    private boolean containsWhitespace(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 认证测试接口
     * 获取当前认证用户的信息
     * 
     * @return 当前认证用户的信息
     */
    @GetMapping("auth-test")
    public Result<SecurityUser> authTest() {
        // 从SecurityContextHolder获取Authentication对象, 然后获取SecurityUser封装用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        // 获取用户信息, 直接返回用户实体类型会将所有信息都返回, 包括账号密码
        return Result.succ(user);
    }
}