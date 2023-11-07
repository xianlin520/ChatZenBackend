package vip.xianlin.entity.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import vip.xianlin.utils.DataEnum;

@Data
@Tag(name = "用户认证信息", description = "用于用户登录")
public class UserAuthVo {
    @Schema(description = "认证主体, 可以是qy号、邮箱、手机号")
    @NotNull
    String principal;
    
    @Schema(description = "用户密码, 加密储存")
    @NotNull
    String password;
    
    @Schema(description = "是否记住登录状态")
    boolean rememberMe;
    
    @Schema(description = "用户状态")
    DataEnum.UserOnlineStatus status;
}