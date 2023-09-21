package vip.xianlin.entity.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

@Data
@Tag(name = "用户认证信息", description = "用于用户登录")
public class UserAuthVo {
    
    @Schema(description = "用户id, 标识, 账号")
    String userId;
    
    @Schema(description = "用户密码, 加密储存")
    String password;
    
    @Schema(description = "用户邮箱")
    String email;
    
    @Schema(description = "用户邮箱验证码")
    String emailCode;
    
    @Schema(description = "用户手机号")
    String phone;
    
    @Schema(description = "用户手机号验证码")
    String phoneCode;
    
    
}