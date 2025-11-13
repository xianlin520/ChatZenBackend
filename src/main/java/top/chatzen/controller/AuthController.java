package top.chatzen.controller;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.chatzen.model.Result;
import top.chatzen.model.SendMailModel;
import top.chatzen.service.IMailService;

import java.util.regex.Pattern;

/**
 * Mail - 身份认证(邮件)
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Resource
    private IMailService mailService;
    
    // 邮箱格式验证正则表达式
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    
    /**
     * 发送简单文本邮件
     * 
     * @param sendMailModel 邮件内容模型，包含收件人、主题和邮件内容
     *                      to: 收件人邮箱地址
     *                      subject: 邮件主题
     *                      content: 邮件正文内容
     * @return 发送结果封装对象
     *         成功时返回状态码200，数据为"发送成功"
     *         失败时返回相应错误信息
     * @throws MessagingException 邮件发送过程中可能出现的异常
     */
    @PostMapping("/mail/send")
    public Result<String> sendMail(@RequestBody SendMailModel sendMailModel) throws MessagingException {
        // 检查参数
        if (!isValidEmail(sendMailModel.getTo())) {
            return Result.fail("邮箱地址格式不正确");
        }
        
        if (!StringUtils.hasText(sendMailModel.getSubject())) {
            return Result.fail("邮件主题不能为空");
        }
        
        if (!StringUtils.hasText(sendMailModel.getContent())) {
            return Result.fail("邮件内容不能为空");
        }
        
        mailService.sendSimpleMail(sendMailModel);
        return Result.succ("发送成功");
    }
    
    /**
     * 发送验证码邮件
     * 该接口会生成6位数字验证码，发送到指定邮箱，并将验证码存储到Redis中5分钟
     * 
     * @param sendMailModel 邮件信息模型
     *                      to: 收件人邮箱地址
     * @return 验证码发送结果
     *         成功时返回状态码200，数据为"验证码发送成功"
     *         失败时返回状态码400，数据为错误信息
     */
    @PostMapping("/mail/send-code")
    public Result<String> sendVerificationCode(@RequestBody SendMailModel sendMailModel) {
        // 检查邮箱参数
        if (!isValidEmail(sendMailModel.getTo())) {
            return Result.fail("邮箱地址格式不正确");
        }
        
        try {
            mailService.sendVerificationCode(sendMailModel);
            return Result.succ("验证码发送成功");
        } catch (Exception e) {
            return Result.fail("验证码发送失败: " + e.getMessage());
        }
    }
    
    /**
     * 验证邮箱验证码
     * 验证用户输入的验证码是否正确，验证成功后会从Redis中删除该验证码
     * 
     * @param sendMailModel 验证信息模型
     *                      to: 收件人邮箱地址
     *                      code: 用户输入的验证码
     * @return 验证结果
     *         成功时返回状态码200，数据为"验证码验证成功"
     *         失败时返回状态码400，数据为"验证码错误或已过期"
     */
    @PostMapping("/mail/verify-code")
    public Result<String> verifyEmailCode(@RequestBody SendMailModel sendMailModel) {
        // 检查参数
        if (!isValidEmail(sendMailModel.getTo())) {
            return Result.fail("邮箱地址格式不正确");
        }
        
        if (!StringUtils.hasText(sendMailModel.getCode())) {
            return Result.fail("验证码不能为空");
        }
        
        // 验证码格式检查（6位数字）
        if (!sendMailModel.getCode().matches("\\d{6}")) {
            return Result.fail("验证码格式不正确");
        }
        
        boolean isValid = mailService.verifyEmailCode(sendMailModel.getTo(), sendMailModel.getCode());
        if (isValid) {
            return Result.succ("验证码验证成功");
        } else {
            return Result.fail("验证码错误或已过期");
        }
    }
    
    /**
     * 验证邮箱地址格式
     * 
     * @param email 邮箱地址
     * @return 格式正确返回true，否则返回false
     */
    private boolean isValidEmail(String email) {
        return StringUtils.hasText(email) && EMAIL_PATTERN.matcher(email).matches();
    }
}