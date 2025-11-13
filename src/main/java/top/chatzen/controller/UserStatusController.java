package top.chatzen.controller;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.chatzen.entity.UserStatus;
import top.chatzen.model.Result;
import top.chatzen.model.SecurityUser;
import top.chatzen.service.IUserStatusService;

/**
 * UserStatus - 用户积分、等级和权限管理
 *
 * @author XianLin
 * @since 2025-10-24
 */
@Slf4j
@RestController
@RequestMapping("/user-status")
public class UserStatusController {
    @Resource
    private IUserStatusService userStatusService;
    
    /**
     * 获取当前用户状态信息
     * 包括积分、等级和权限等信息
     * 
     * @return 当前用户的状态信息
     */
    @GetMapping("/info")
    public Result<UserStatus> getInfo() {
        // 从SecurityContextHolder获取Authentication对象, 然后获取SecurityUser封装用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        // 获取用户信息, 直接返回实体类型会将所有信息都返回
        UserStatus userStatusById = userStatusService.getUserStatusById(user.getUserId());
        return Result.succ(userStatusById);
    }
    
    /**
     * 更新当前用户积分
     * 
     * @param points 要更新的积分值
     * @return 更新结果
     */
    @GetMapping("/update/points/{points}")
    public Result<String> update(@PathVariable("points") int points) {
        
        boolean b = userStatusService.updateUserPointByNow(points);
        if (b) {
            return Result.succ("用户状态更新成功");
        }
        return Result.succ("用户状态更新失败");
    }
    
    /**
     * 更新当前用户等级
     * 根据用户积分自动升级用户等级
     * 
     * @return 更新结果
     * @throws MessagingException 邮件发送异常
     */
    @GetMapping("/update/level")
    public Result<String> updateLevel() throws MessagingException {
        boolean b = userStatusService.updateUserLevelByNow();
        if (!b) return Result.succ("用户状态更新失败");
        return Result.succ("用户状态更新成功");
    }
}