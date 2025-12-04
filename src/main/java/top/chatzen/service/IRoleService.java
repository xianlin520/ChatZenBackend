package top.chatzen.service;

import top.chatzen.entity.UserAccount;

/**
 * 角色管理服务接口
 */
public interface IRoleService {
    /**
     * 验證角色是否為系統預定義的有效角色
     * @param role 角色字符串
     * @return 是否為有效角色
     */
    boolean isValidRole(String role);
    
    /**
     * 設置用戶角色，僅在角色有效時設置
     * @param userAccount 用戶賬戶
     * @param role 角色
     * @return 是否設置成功
     */
    boolean setRoleSafely(UserAccount userAccount, String role);
    
    /**
     * 獲取默認角色
     * @return 默認角色
     */
    String getDefaultRole();
}