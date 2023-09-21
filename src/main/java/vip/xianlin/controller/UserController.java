package vip.xianlin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xianlin.entity.Result;
import vip.xianlin.utils.JwtUtils;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 羡林i
 * @since 2023-09-20
 */
@RestController
@RequestMapping("user")
@Tag(name = "用户操作", description = "用户注销, 查询, 更新等操作")
public class UserController {
    
    @Resource
    JwtUtils utils;
    
    @Operation(summary = "用户注销", description = "用户注销登录, Token失效", security = {@SecurityRequirement(name = "Authorization")})
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (utils.invalidateJwt(authorization)) {
            return Result.succ("退出登录成功");
        }
        return Result.fail("退出登录失败");
    }
}
