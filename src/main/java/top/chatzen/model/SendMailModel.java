package top.chatzen.model;

import lombok.Data;

@Data
public class SendMailModel {
    private String to;
    private String subject;
    private String content;
    // 添加验证码字段
    private String code;
}