package top.chatzen.service.impl;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import top.chatzen.model.SendMailModel;
import top.chatzen.service.IMailService;

@Slf4j
@Service
public class MailServiceImpl implements IMailService {
    
    @Resource
    private JavaMailSender mailSender;
    
    // 从配置文件中读取发件人邮箱
    @Value("${spring.mail.username}")
    private String from;
    
    
    @Override
    @Async // 将方法设为异步, 防止堵塞
    public void sendSimpleMail(SendMailModel sendMailModel) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("千寻云(羡林i) <" + from + ">");
        message.setTo(sendMailModel.getTo());
        message.setSubject(sendMailModel.getSubject());
        message.setText(sendMailModel.getContent());
        // 休眠5秒
        try {
            mailSender.send(message);
            log.info("简单文本邮件发送成功！收件人：{}", sendMailModel.getTo());
        } catch (Exception e) {
            log.error("发送简单文本邮件时发生异常！", e);
        }
        
    }
    
    @Override
    @Async
    public void sendHtmlMail(SendMailModel sendMailModel) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(sendMailModel.getTo());
        helper.setSubject(sendMailModel.getSubject());
        // 第二个参数为true，表示text内容是HTML格式
        helper.setText(sendMailModel.getContent(), true);
        
        mailSender.send(message);
        log.info("HTML邮件发送成功！收件人：{}", sendMailModel.getTo());
    }
    
    @Override
    @Async
    public void sendAttachmentMail(String to, String subject, String content, String filePath) {
    
    }
}
