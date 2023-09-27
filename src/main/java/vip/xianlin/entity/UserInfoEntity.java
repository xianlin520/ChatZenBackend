package vip.xianlin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author 羡林i
 * @since 2023-09-27
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("user_info")
@Schema(name = "UserInfoEntity", description = "$!{table.comment}")
public class UserInfoEntity extends Model<UserInfoEntity> {
    
    @Schema(description = "外键，引用自 user 表的 user_id 主键")
    @TableField("user_id")
    private Integer userId;
    
    @Schema(description = "个性签名，默认值为一个默认提示")
    @TableField("signature")
    private String signature;
    
    @Schema(description = "简介，默认为空")
    @TableField("introduction")
    private String introduction;
    
    @Schema(description = "生日, 默认为空")
    @TableField("birthday")
    private Date birthday;
    
    @Schema(description = "标签，用逗号分隔的字符串, 默认为空")
    @TableField("tags")
    private String tags;
    
    @Schema(description = "用户地址, 默认为空")
    @TableField("address")
    private String address;
    
    @Schema(description = "用户自定义唯一标识, 类ID, 默认为空")
    @TableField("uid")
    private String uid;
    
    @Override
    public Serializable pkVal() {
        return null;
    }
}
