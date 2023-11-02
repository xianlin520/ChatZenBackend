package vip.xianlin.entity.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import java.util.Date;

@Data
@Tag(name = "用户基本信息", description = "用户基本信息")
public class UserBasicInfoVo {
    @Schema(description = "用户id")
    Integer userId;
    
    @Schema(description = "用户昵称")
    String userName;
    
    @Schema(description = "用户邮箱")
    String email;
    
    @Schema(description = "用户头像")
    String headThumb;
    
    @Schema(description = "用户角色")
    String role;
    
    @Schema(description = "用户最后登录时间")
    Date lastLoginTime;
}
