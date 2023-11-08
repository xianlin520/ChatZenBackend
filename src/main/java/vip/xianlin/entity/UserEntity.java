package vip.xianlin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import vip.xianlin.utils.DataEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author 羡林i
 * @since 2023-11-07
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("user")
@Schema(name = "UserEntity", description = "$!{table.comment}")
public class UserEntity extends Model<UserEntity> {
    
    @Schema(description = "用户id, 标识, 账号")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;
    
    @Schema(description = "用户昵称, 可改")
    @TableField("user_name")
    private String userName;
    
    @Schema(description = "用户角色, 权限")
    @TableField("role")
    private String role;
    
    @Schema(description = "用户密码, 加密储存")
    @TableField("password")
    private String password;
    
    @Schema(description = "用户注册邮箱")
    @TableField("email")
    private String email;
    
    @Schema(description = "用户绑定手机号")
    @TableField("phone")
    private String phone;
    
    @Schema(description = "用户头像")
    @TableField("head_thumb")
    private String headThumb;
    
    @Schema(description = "用户注册时间")
    @TableField("register_time")
    private Date registerTime;
    
    @Schema(description = "用户最后登录时间")
    @TableField("last_login_time")
    private Date lastLoginTime;
    
    @Schema(description = "用户是否注销,默认否")
    @TableField("is_deleted")
    private Boolean deleted;
    
    @Schema(description = "用户当前状态,默认离线")
    @TableField("status")
    private DataEnum.UserOnlineStatus status;
    
    public String[] getRoles() {
        // 读取字符串, 以逗号分隔, 转换为数组
        return role.split(",");
    }
    
    @Override
    public Serializable pkVal() {
        return this.userId;
    }
}
