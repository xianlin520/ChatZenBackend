package vip.xianlin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.xianlin.entity.UserEntity;
import vip.xianlin.mapper.UserMapper;
import vip.xianlin.service.IUserService;


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
    
}
