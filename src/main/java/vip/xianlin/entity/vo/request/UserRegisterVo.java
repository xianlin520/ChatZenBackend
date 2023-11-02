package vip.xianlin.entity.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Tag(name = "用户注册信息", description = "用于用户注册")
public class UserRegisterVo {
    @Schema(description = "用户昵称")
    @NotNull
    String nickname;
    
    @Schema(description = "用户邮箱")
    @NotNull
    String email;
    
    @Schema(description = "用户邮箱验证码")
    @NotNull
    String emailCode;
    
    @Schema(description = "用户密码, 加密储存")
    @NotNull
    String password;
}
