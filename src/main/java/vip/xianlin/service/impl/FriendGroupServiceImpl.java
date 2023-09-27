package vip.xianlin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.xianlin.entity.FriendGroupEntity;
import vip.xianlin.mapper.FriendGroupMapper;
import vip.xianlin.service.IFriendGroupService;

/**
 * <p>
 * 用户好友分组信息表, 用于保存用户创建的分组 服务实现类
 * </p>
 *
 * @author 羡林i
 * @since 2023-09-27
 */
@Service
public class FriendGroupServiceImpl extends ServiceImpl<FriendGroupMapper, FriendGroupEntity> implements IFriendGroupService {

}
