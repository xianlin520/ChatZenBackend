package vip.xianlin.service;

public interface IAuthorityService {
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
