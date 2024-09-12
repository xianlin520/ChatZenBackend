package top.chatzen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author XianLin
 * @since 2024-09-11
 */
@Getter
@Setter
@TableName("t_user_account")
public class UserAccount implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 用户账号-唯一
     */
    @TableField("username")
    private String username;
    
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
}
