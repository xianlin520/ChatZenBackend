package vip.xianlin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.xianlin.entity.FriendRecordEntity;
import vip.xianlin.mapper.FriendRecordMapper;
import vip.xianlin.service.IFriendRecordService;

/**
 * <p>
 * 用于存储用户的好友关系信息 服务实现类
 * </p>
 *
 * @author 羡林i
 * @since 2023-09-27
 */
@Service
public class FriendRecordServiceImpl extends ServiceImpl<FriendRecordMapper, FriendRecordEntity> implements IFriendRecordService {

}
