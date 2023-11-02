package vip.xianlin.entity.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

@Data
@Tag(name = "用户认证信息", description = "用于用户登录")
public class UserAuthVo {
    @Schema(description = "认证主体, 可以是qy号、邮箱、手机号")
    String principal;
    
    @Schema(description = "用户密码, 加密储存")
    String password;
}