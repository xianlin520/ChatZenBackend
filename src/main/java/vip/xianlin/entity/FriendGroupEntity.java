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

import java.io.Serializable;

/**
 * <p>
 * 用户好友分组信息表, 用于保存用户创建的分组
 * </p>
 *
 * @author 羡林i
 * @since 2023-09-27
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("friend_group")
@Schema(name = "FriendGroupEntity", description = "$!{table.comment}")
public class FriendGroupEntity extends Model<FriendGroupEntity> {
    
    @Schema(description = "分组唯一标识符")
    @TableId(value = "group_id", type = IdType.AUTO)
    private Integer groupId;
    
    @Schema(description = "此分组的用户, 引用userid")
    @TableField("user_id")
    private Integer userId;
    
    @Schema(description = "分组名称, 设默认值")
    @TableField("group_name")
    private String groupName;
    
    @Schema(description = "分组排序权重, 默认1")
    @TableField("sort_weight")
    private Integer sortWeight;
    
    @Override
    public Serializable pkVal() {
        return this.groupId;
    }
}
