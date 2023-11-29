package vip.xianlin.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import vip.xianlin.entity.ws.PrivateMessage;

@RestController
@Slf4j
public class WsController {
    
    @Resource
    private SimpMessagingTemplate messagingTemplate;
    
    
    @MessageMapping("/private.message")
    public void handlePrivateMessage(PrivateMessage privateMessage) {
        log.info("接收到私聊消息：{}", privateMessage);
        messagingTemplate.convertAndSendToUser(
                privateMessage.getRecipient(),
                "/private",
                privateMessage);
    }
}
