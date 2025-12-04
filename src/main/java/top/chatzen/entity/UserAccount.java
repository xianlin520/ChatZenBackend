package top.chatzen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author XianLin
 * @since 2025-10-24
 */
@Getter
@Setter
@TableName("t_user_account")
public class UserAccount implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 数据ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 用户ID, 使用雪花算法生成-唯一
     */
    @TableField("user_id")
    private Long userId;
    
    /**
     * 用户昵称
     */
    @TableField("name")
    private String name;
    
    /**
     * 用户账号-唯一
     */
    @TableField("account")
    private String account;
    
    /**
     * 用户密码-加密
     */
    @TableField("password_hash")
    private String passwordHash;
    
    /**
     * 账号创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 用户角色
     */
    @TableField("role")
    private String role;  // 角色通常由系统管理，不应由普通用户随意修改
}
