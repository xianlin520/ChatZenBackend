package vip.xianlin.service;

import vip.xianlin.entity.UserEntity;

public interface IAuthorityService {
    /**
     * 根据邮箱/手机号获取用户信息
     *
     * @param principal 邮箱/手机号
     * @return 用户信息
     */
    UserEntity getUserByPrincipal(String principal);
    
    /**
     * 验证邮箱/手机号和验证码是否匹配
     *
     * @param principal  邮箱/手机号
     * @param verifyCode 验证码
     * @return 返回用户对象
     */
    boolean verifyCode(String principal, String verifyCode);
    
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
