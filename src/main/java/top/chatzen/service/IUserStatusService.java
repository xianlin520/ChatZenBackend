package top.chatzen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.mail.MessagingException;
import top.chatzen.entity.UserStatus;

/**
 * <p>
 * 储存用户积分, 用户等级, 用户权限等 服务类
 * </p>
 *
 * @author XianLin
 * @since 2025-10-24
 */
public interface IUserStatusService extends IService<UserStatus> {
    
    /**
     * 提供一个Userid, 新增用户等级数据, 其他为默认值
     *
     * @param userId 注册账号时生成的雪花ID
     * @return 是否添加成功(bool)
     */
    boolean addUserStatusById(long userId);
    
    /**
     * 提供Userid, 返回用户等级数据
     *
     * @param userId 注册账号时生成的雪花ID
     * @return 用户等级数据
     */
    UserStatus getUserStatusById(long userId);
    
    /**
     * 提供Userid和更新数据, 更新用户等级数据
     *
     * @param userId     注册账号时生成的雪花ID
     * @param userStatus 更新数据
     * @return 是否更新成功(bool)
     */
    boolean updateUserStatusById(long userId, UserStatus userStatus);
    
    /**
     * 提供Userid和积分, 更新用户积分
     *
     * @param userId 注册账号时生成的雪花ID
     * @param points 积分
     * @return 是否更新成功(bool)
     */
    boolean updateUserPointsById(long userId, int points);
    
    /**
     * 根据当前上下文用户, 更新积分
     *
     * @param points 积分
     * @return 是否更新成功(bool)
     */
    boolean updateUserPointByNow(int points);
    
    /**
     * 提供Userid和等级, 更新用户等级
     *
     * @param userId 注册账号时生成的雪花ID
     * @return 是否更新成功(bool)
     */
    boolean updateUserLevelById(long userId) throws MessagingException;
    
    /**
     * 根据当前上下文用户, 更新用户等级
     *
     * @return 是否更新成功(bool)
     */
    boolean updateUserLevelByNow() throws MessagingException;
    
}
