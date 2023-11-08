package vip.xianlin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xianlin.entity.*;
import vip.xianlin.mapper.UserMapper;
import vip.xianlin.service.*;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author XianLin
 * @since 2023-09-18
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements IUserService {
    
    // 引入用户分组业务层
    @Resource
    private IFriendGroupService friendGroupService;
    
    // 引入用户好友业务层
    @Resource
    private IFriendRecordService friendRecordService;
    
    // 引入好友消息业务层
    @Resource
    private IPrivateChatRecordService privateChatRecordService;
    
    // 引入用户信息业务层
    @Resource
    private IUserInfoService userInfoService;
    
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(UserEntity userEntity) {
        // 添加用户
        save(userEntity);
        // 通过邮箱查询刚刚添加的用户
        UserEntity user = getOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getEmail, userEntity.getEmail()));
        // 默认添加用户信息数据
        userInfoService.save(new UserInfoEntity().setUserId(user.getUserId()));
        // 默认添加用户好友分组数据
        friendGroupService.save(new FriendGroupEntity().setUserId(user.getUserId()));
        // 查询刚刚添加的用户分组
        FriendGroupEntity friendGroup = friendGroupService.getOne(new QueryWrapper<FriendGroupEntity>().lambda().eq(FriendGroupEntity::getUserId, user.getUserId()));
        // 默认为用户添加一个系统好友
        friendRecordService.save(new FriendRecordEntity()
                .setUserId(user.getUserId()) // 用户id
                .setFriendId(1000) // 系统好友
                .setGroupId(friendGroup.getGroupId()) // 默认分组
        );
        // 查询刚刚添加的用户好友
        FriendRecordEntity friendRecord = friendRecordService.getOne(new QueryWrapper<FriendRecordEntity>().lambda().eq(FriendRecordEntity::getUserId, user.getUserId()));
        // 默认为系统好友添加一条消息记录
        privateChatRecordService.save(new PrivateChatRecordEntity()
                .setUserId(friendRecord.getUserId()) // 发送者
                .setReceiveId(friendRecord.getFriendId()) // 接收者
                .setRecordContent("您好! 欢迎您使用轻语阁在线聊天系统") // 消息内容
        );
        return true;
    }
}
