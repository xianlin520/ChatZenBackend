package top.chatzen.constant;

public class Const {
    // 创建角色枚举
    public enum Role {
        USER, ADMIN, TEST, BAN, GUEST
    }
    //Redis存储
    public final static String REDIS_KEY= "auth_token_key:%s";
    //JWT令牌
    public final static String JWT_BLACK_LIST = "jwt:blacklist:";
    public final static String JWT_FREQUENCY = "jwt:frequency:";
    //请求频率限制
    public final static String FLOW_LIMIT_COUNTER = "flow:counter:";
    public final static String FLOW_LIMIT_BLOCK = "flow:block:";
    //邮件验证码
    public final static String VERIFY_EMAIL_LIMIT = "verify:email:limit:";
    public final static String VERIFY_EMAIL_DATA = "verify:email:data:";
    // 手机号验证码
    public final static String VERIFY_PHONE_LIMIT = "verify:phone:limit:";
    public final static String VERIFY_PHONE_DATA = "verify:phone:data:";
    //过滤器优先级
    public final static int ORDER_FLOW_LIMIT = -101;
    public final static int ORDER_CORS = -102;
    //请求自定义属性
    public final static String ATTR_USER_ID = "userId";
    //消息队列
    public final static String MQ_MAIL = "mail";
    public final static String MQ_WS_SEND = "ws:send";
}
