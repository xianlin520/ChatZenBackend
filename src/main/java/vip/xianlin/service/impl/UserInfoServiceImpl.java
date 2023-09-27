package vip.xianlin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.xianlin.entity.UserInfoEntity;
import vip.xianlin.mapper.UserInfoMapper;
import vip.xianlin.service.IUserInfoService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 羡林i
 * @since 2023-09-27
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfoEntity> implements IUserInfoService {

}
