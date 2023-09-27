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
import java.util.Date;

/**
 * <p>
 * 用于存储用户的好友关系信息
 * </p>
 *
 * @author 羡林i
 * @since 2023-09-27
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("friend_record")
@Schema(name = "FriendRecordEntity", description = "$!{table.comment}")
public class FriendRecordEntity extends Model<FriendRecordEntity> {
    
    @Schema(description = "好友表唯一标识符")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    @Schema(description = "当前用户id, 关联userid")
    @TableField("user_id")
    private Integer userId;
    
    @Schema(description = "当前用户的好友id, 关联userid")
    @TableField("friend_id")
    private Integer friendId;
    
    @Schema(description = "好友备注, 默认为空")
    @TableField("friend_name")
    private String friendName;
    
    @Schema(description = "分组id, 关联groupid")
    @TableField("group_id")
    private Integer groupId;
    
    @Schema(description = "添加好友时间, 默认为当前时间")
    @TableField("added_time")
    private Date addedTime;
    
    @Schema(description = "消息通知等级, 默认为2")
    @TableField("notice_level")
    private Integer noticeLevel;
    
    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
