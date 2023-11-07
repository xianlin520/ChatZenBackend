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
 * 私聊聊天记录表
 * </p>
 *
 * @author 羡林i
 * @since 2023-11-07
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("private_chat_record")
@Schema(name = "PrivateChatRecordEntity", description = "$!{table.comment}")
public class PrivateChatRecordEntity extends Model<PrivateChatRecordEntity> {
    
    @Schema(description = "记录表唯一标识")
    @TableId(value = "record_id", type = IdType.AUTO)
    private Integer recordId;
    
    @Schema(description = "发送者, 关联关系表userId")
    @TableField("user_id")
    private Integer userId;
    
    @Schema(description = "接收者id, 绑定关系表friend_id")
    @TableField("receive_id")
    private Integer receiveId;
    
    @Schema(description = "消息类型")
    @TableField("type")
    private String type;
    
    @Schema(description = "消息记录内容")
    @TableField("record_content")
    private String recordContent;
    
    @Schema(description = "记录消息状态")
    @TableField("status")
    private String status;
    
    @Schema(description = "发送消息时间, 默认当前时间")
    @TableField("send_time")
    private Date sendTime;
    
    @Override
    public Serializable pkVal() {
        return this.recordId;
    }
}
