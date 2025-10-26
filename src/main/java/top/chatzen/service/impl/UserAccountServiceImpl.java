package top.chatzen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.chatzen.dao.UserAccountMapper;
import top.chatzen.entity.UserAccount;
import top.chatzen.interrupt.BizException;
import top.chatzen.service.IUserAccountService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author XianLin
 * @since 2024-09-11
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements IUserAccountService {
    @Resource
    private PasswordEncoder passwordEncoder;
    
    @Override
    public boolean save(UserAccount entity) {
        // TODO 修改保存逻辑
        // 查询用户账号是否重复
        UserAccount userAccount = lambdaQuery().eq(UserAccount::getAccount, entity.getAccount()).one();
        if (userAccount != null) {
            throw new BizException("该账号已被注册");
        }
        // 密码加密
        entity.setPasswordHash(passwordEncoder.encode(entity.getPasswordHash()));
        return super.save(entity);
    }
    
    @Override
    public void saveUserAccount(UserAccount userAccount) {
        // TODO 保存用户信息
    }
    
    @Override
    public UserAccount getUserAccountByUserId(Long userId) {
        return lambdaQuery().eq(UserAccount::getId, userId).one();
    }
}
