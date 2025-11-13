package top.chatzen.service.impl;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import top.chatzen.constant.Const;
import top.chatzen.model.SendMailModel;
import top.chatzen.service.IMailService;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MailServiceImpl implements IMailService {
    
    @Resource
    private JavaMailSender mailSender;
    
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    
    // 从配置文件中读取发件人邮箱
    @Value("${spring.mail.username}")
    private String from;
    
    
    @Override
    public void sendVerificationCode(SendMailModel sendMailModel) throws MessagingException {
        // 生成6位随机数字验证码
        String verificationCode = String.format("%06d", new Random().nextInt(999999));
        
        // 构造邮件内容
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("千寻云(羡林i) <" + from + ">");
        message.setTo(sendMailModel.getTo());
        message.setSubject("【千寻云】验证码");
        message.setText("您的验证码是：" + verificationCode + "，5分钟内有效。如非本人操作，请忽略此邮件。");
        
        // 发送邮件
        mailSender.send(message);
        log.info("验证码邮件发送成功！收件人：{}，验证码：{}", sendMailModel.getTo(), verificationCode);
        
        // 将验证码存入Redis，设置5分钟过期时间
        redisTemplate.opsForValue().set(
                String.format(Const.VERIFY_EMAIL_DATA, sendMailModel.getTo()),
                verificationCode,
                5,
                TimeUnit.MINUTES);
    }
    
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
    public void sendAttachmentMail(String to, String subject, String content, String filePath) throws MessagingException {
    
    }
    
    /**
     * 验证邮箱验证码
     * @param email 邮箱地址
     * @param code 验证码
     * @return 验证结果
     */
    @Override
    public boolean verifyEmailCode(String email, String code) {
        String key = String.format(Const.VERIFY_EMAIL_DATA, email);
        String storedCode = (String) redisTemplate.opsForValue().get(key);
        
        // 检查验证码是否存在且匹配
        if (storedCode != null && storedCode.equals(code)) {
            // 验证成功后删除验证码
            redisTemplate.delete(key);
            return true;
        }
        return false;
    }
}