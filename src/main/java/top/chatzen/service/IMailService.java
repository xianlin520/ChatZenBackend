package top.chatzen.service;


import jakarta.mail.MessagingException;
import top.chatzen.model.SendMailModel;

public interface IMailService {
    
    /**
     * 发送简单文本邮件
     *
     * @param sendMailModel 邮件内容
     * @throws MessagingException
     */
    void sendSimpleMail(SendMailModel sendMailModel) throws MessagingException;
    
    /**
     * 发送HTML邮件
     *
     * @param sendMailModel 邮件内容
     * @throws MessagingException
     */
    void sendHtmlMail(SendMailModel sendMailModel) throws MessagingException;
    
    /**
     * 发送带附件的邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param content  内容
     * @param filePath 附件路径
     * @throws MessagingException
     */
    void sendAttachmentMail(String to, String subject, String content, String filePath) throws MessagingException;
}
