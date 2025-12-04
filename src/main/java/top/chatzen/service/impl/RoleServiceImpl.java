package top.chatzen.service.impl;

import org.springframework.stereotype.Service;
import top.chatzen.constant.Const;
import top.chatzen.entity.UserAccount;
import top.chatzen.service.IRoleService;

/**
 * 角色管理服务实现
 */
@Service
public class RoleServiceImpl implements IRoleService {

    /**
     * 驗证角色是否為系統預定義的有效角色
     * @param role 角色字符串
     * @return 是否為有效角色
     */
    @Override
    public boolean isValidRole(String role) {
        if (role == null) {
            return false;
        }

        try {
            Const.Role.valueOf(role.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * 设置用户角色，仅在角色有效时设值
     * @param userAccount 用户账户
     * @param role 角色
     * @return 是否设值成功
     */
    @Override
    public boolean setRoleSafely(UserAccount userAccount, String role) {
        if (isValidRole(role)) {
            userAccount.setRole(role.toUpperCase());
            return true;
        }
        return false;
    }

    /**
     * 获取默认角色
     * @return 默认角色
     */
    @Override
    public String getDefaultRole() {
        return Const.Role.USER.name();
    }
    
}