package top.chatzen.service;

import top.chatzen.entity.UserAccount;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XianLin
 * @since 2024-09-11
 */
public interface IUserAccountService extends IService<UserAccount> {
    void saveUserAccount(UserAccount userAccount);
    
    /**
     * 根据用户id获取用户账户信息
     * @param userId 用户id
     * @return 用户信息, 如果没有则返回null
     */
    UserAccount getUserAccountByUserId(Long userId);
}
