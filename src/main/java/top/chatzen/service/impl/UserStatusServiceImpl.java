package top.chatzen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.chatzen.dao.UserStatusMapper;
import top.chatzen.entity.UserStatus;
import top.chatzen.model.SendMailModel;
import top.chatzen.service.IMailService;
import top.chatzen.service.IUserStatusService;
import top.chatzen.util.SecurityUtils;

/**
 * <p>
 * 储存用户积分, 用户等级, 用户权限等 服务实现类
 * </p>
 *
 * @author XianLin
 * @since 2025-10-24
 */
@Service
@Slf4j
public class UserStatusServiceImpl extends ServiceImpl<UserStatusMapper, UserStatus> implements IUserStatusService {
    @Resource
    IMailService mailService;
    
    
    @Override
    public boolean addUserStatusById(long userId) {
        UserStatus userStatus = new UserStatus();
        userStatus.setUserId(userId);
        return save(userStatus);
    }
    
    @Override
    public UserStatus getUserStatusById(long userId) {
        // 查询字段user_id, 并返回数据
        UserStatus one = lambdaQuery().eq(UserStatus::getUserId, userId).one();
        return one;
    }
    
    @Override
    public boolean updateUserStatusById(long userId, UserStatus userStatus) {
        // 根据user_id 更新数据
        return lambdaUpdate()
                .eq(UserStatus::getUserId, userId)
                .set(UserStatus::getId, null)
                .set(UserStatus::getUserId, null)
                .update();
    }
    
    @Override
    public boolean updateUserPointsById(long userId, int points) {
        // 更新用户积分
        return lambdaUpdate()
                .eq(UserStatus::getUserId, userId) // WHERE user_id = ?
                .set(UserStatus::getPoints, points) // SET points = ?
                .update(); // 执行更新
    }
    
    @Override
    public boolean updateUserPointByNow(int points) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        return updateUserPointsById(currentUserId, points);
    }
    
    @Override
    public boolean updateUserLevelById(long userId) throws MessagingException {
        // 查询用户积分
        UserStatus userStatus = getUserStatusById(userId);
        if (userStatus == null) {
            return false;
        }
        if (userStatus.getPoints() >= 1000) {
            userStatus.setLevel(2);
            userStatus.setName("VIP用户");
            SendMailModel sendMailModel = new SendMailModel();
            sendMailModel.setTo("15653674386@163.com");
            sendMailModel.setSubject("用户等级提升");
            sendMailModel.setContent("您的用户用户等级已提升至VIP用户, 感谢您的支持");
            // 新建线程去运行发送邮件
            mailService.sendSimpleMail(sendMailModel);
        }
        return updateById(userStatus);
    }
    
    @Override
    public boolean updateUserLevelByNow() throws MessagingException {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        return updateUserLevelById(currentUserId);
    }
}
