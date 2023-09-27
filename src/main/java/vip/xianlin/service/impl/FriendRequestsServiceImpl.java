package vip.xianlin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.xianlin.entity.FriendRequestsEntity;
import vip.xianlin.mapper.FriendRequestsMapper;
import vip.xianlin.service.IFriendRequestsService;

/**
 * <p>
 * 好友请求记录表 服务实现类
 * </p>
 *
 * @author 羡林i
 * @since 2023-09-27
 */
@Service
public class FriendRequestsServiceImpl extends ServiceImpl<FriendRequestsMapper, FriendRequestsEntity> implements IFriendRequestsService {

}
