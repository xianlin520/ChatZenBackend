package top.chatzen.controller;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.chatzen.model.Result;
import top.chatzen.model.SendMailModel;
import top.chatzen.service.IMailService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Resource
    private IMailService mailService;
    
    @PostMapping("/mail/send")
    public Result<String> sendMail(@RequestBody SendMailModel sendMailModel) throws MessagingException {
        mailService.sendSimpleMail(sendMailModel);
        return Result.succ("发送成功");
    }
}
