package top.chatzen.service.impl;

import top.chatzen.entity.UserAccount;
import top.chatzen.dao.UserAccountMapper;
import top.chatzen.service.IUserAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XianLin
 * @since 2024-09-11
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements IUserAccountService {

}
