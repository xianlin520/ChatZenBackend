package vip.xianlin.utils;

/**
 * <p>
 * 数据库字段类型枚举
 */
public class DataEnum {
    // 邮件类型枚举
    public enum EmailType {
        REGISTER, LOGIN, RESET
    }
    
    // 用户角色枚举
    public enum UserRole {
        USER, ADMIN, TEST, BAN, GUEST
    }
    
    // 用户状态枚举
    public enum UserOnlineStatus {
        ONLINE, OFFLINE, INVISIBLE, BUSY, DO_NOT_DISTURB
    }
    
    // 好友请求状态枚举
    public enum FriendRequestStatus {
        PENDING, ACCEPTED, REJECTED
    }
    
    // 好友通知等级枚举
    public enum FriendNotificationLevel {
        DEFAULT, DND, BLOCKED, STARRED
    }
    
    // 消息文件类型枚举
    public enum MessageFileType {
        TEXT, IMAGE, AUDIO, VIDEO, FILE
    }
    
    // 消息发送状态枚举
    public enum MessageSendStatus {
        NOT_SENT, RESENT, READ, RECALLED, SEND_FAILED
    }
    
}
