package vip.xianlin.entity.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Tag(name = "用户认证信息", description = "用于用户登录")
public class UserCodeAuthVo {
    @Schema(description = "认证主体- 邮箱、手机号")
    @NotNull
    String principal;
    
    @Schema(description = "验证码")
    @NotNull // 验证码不能为空
    String authCode;
    
}