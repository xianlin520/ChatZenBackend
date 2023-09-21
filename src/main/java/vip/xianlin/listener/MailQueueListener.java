package vip.xianlin.listener;

import jakarta.annotation.Resource;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Arrays;
import java.util.Map;


// 邮件队列监听器, 消费者
@Component
@Slf4j
@RabbitListener(queues = "mail")
public class MailQueueListener {
    
    // 注入邮件发送服务
    @Resource
    JavaMailSender mailSender;
    
    // 注入模板引擎
    @Resource
    TemplateEngine templateEngine;
    
    // 注入邮件发送者
    @Value("${spring.mail.username}")
    String username;
    
    @RabbitHandler
    public void sendMailMessage(Map<String, Object> map) {
        // 获取邮件信息
        String email = (String) map.get("email");
        Integer code = (Integer) map.get("code");
        String type = (String) map.get("type");
        try {
            //创建邮件正文
            Context context = new Context();
            context.setVariable("verifyCode", Arrays.asList(code.toString().split("")));
            
            //将模块引擎内容解析成html字符串
            String emailContent = switch (type) {
                case "register" -> templateEngine.process("EmailRegisterCode", context);
                case "login" -> templateEngine.process("EmailLoginCode", context);
                case "reset" -> templateEngine.process("EmailResetCode", context);
                default -> templateEngine.process("EmailCode", context);
            };
            MimeMessage message = mailSender.createMimeMessage();
            String sendName = MimeUtility.encodeText("千寻云");
            
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setFrom(fromAddress);
            helper.setFrom(new InternetAddress(sendName + " <" + username + ">"));
            
            helper.setTo(email); // 设置收件人
            helper.setSubject("轻语阁-邮箱验证码"); // 设置邮件标题
            helper.setText(emailContent, true); // 设置邮件内容
            mailSender.send(message); // 发送邮件
        } catch (Exception e) {
            log.error("邮件验证码发送失败, 收件人:" + email);
        }
    }
}
