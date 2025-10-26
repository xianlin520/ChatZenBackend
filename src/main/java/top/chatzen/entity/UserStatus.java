package top.chatzen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 储存用户积分, 用户等级, 用户权限等
 * </p>
 *
 * @author XianLin
 * @since 2025-10-24
 */
@Getter
@Setter
@TableName("t_user_status")
public class UserStatus implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键_自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 用户id-外键关联-唯一
     */
    @TableField("user_id")
    private Long userId;
    
    /**
     * 用户积分
     */
    @TableField("points")
    private Integer points;
    
    /**
     * 用户权限等级
     */
    @TableField("level")
    private Integer level;
    
    /**
     * 用户权限等级名
     */
    @TableField("name")
    private String name;
}
