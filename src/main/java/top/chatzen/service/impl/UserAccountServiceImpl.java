package top.chatzen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.chatzen.dao.UserAccountMapper;
import top.chatzen.entity.UserAccount;
import top.chatzen.interrupt.BizException;
import top.chatzen.service.IRoleService;
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

    @Resource
    private IRoleService roleService;
    
    @Override
    public boolean save(UserAccount entity) {
        // 查询用户账号是否重复
        UserAccount userAccount = lambdaQuery().eq(UserAccount::getAccount, entity.getAccount()).one();
        if (userAccount != null) {
            throw new BizException("该账号已被注册");
        }
        // 密码加密
        entity.setPasswordHash(passwordEncoder.encode(entity.getPasswordHash()));
        // 对于新注册的用户，始终分配默认角色，忽略用户可能指定的角色
        entity.setRole(roleService.getDefaultRole());
        return super.save(entity);
    }
    
    @Override
    public void saveUserAccount(UserAccount userAccount) {
        // 保存用户信息
        save(userAccount);
    }
    
    @Override
    public UserAccount getUserAccountByUserId(Long userId) {
        // 使用userId字段查询，而不是id字段
        return lambdaQuery().eq(UserAccount::getUserId, userId).one();
    }
}