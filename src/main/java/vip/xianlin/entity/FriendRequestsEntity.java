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
 * 好友请求记录表
 * </p>
 *
 * @author 羡林i
 * @since 2023-09-27
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("friend_requests")
@Schema(name = "FriendRequestsEntity", description = "$!{table.comment}")
public class FriendRequestsEntity extends Model<FriendRequestsEntity> {
    
    @Schema(description = "请求表唯一标识")
    @TableId(value = "request_id", type = IdType.AUTO)
    private Integer requestId;
    
    @Schema(description = "请求人填写的请求消息")
    @TableField("requester_msg")
    private String requesterMsg;
    
    @Schema(description = "请求人id, 关联userid")
    @TableField("requester_id")
    private Integer requesterId;
    
    @Schema(description = "接收人id, 关联userid")
    @TableField("receiver_id")
    private Integer receiverId;
    
    @Schema(description = "请求状态, 默认为2")
    @TableField("status")
    private Integer status;
    
    @Schema(description = "请求时间, 默认为当前时间")
    @TableField("send_time")
    private Date sendTime;
    
    @Override
    public Serializable pkVal() {
        return this.requestId;
    }
}
