package vip.xianlin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.xianlin.entity.UserEntity;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author XianLin
 * @since 2023-09-18
 */
public interface IUserService extends IService<UserEntity> {
    
    /**
     * 发送邮箱验证码, 有效期5分钟
     * 60S内只能发送一次
     *
     * @param type  验证码类型
     * @param email 邮箱地址
     * @param ip    IP地址
     * @return 限流剩余时间, 0表示发送成功
     */
    long askEmailVerifyCode(String type, String email, String ip);
    
}
