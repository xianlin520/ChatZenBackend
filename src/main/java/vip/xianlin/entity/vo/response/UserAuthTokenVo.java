package vip.xianlin.entity.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@ApiModel(value = "用户认证令牌信息")
public class UserAuthTokenVo {
    
    @Schema(description = "用户id")
    Integer userId;
    
    @Schema(description = "用户角色")
    String role;
    
    @Schema(description = "用户昵称")
    String userName;
    
    @Schema(description = "用户头像")
    String headThumb;
    
    @Schema(description = "用户Token")
    String token;
    
}
