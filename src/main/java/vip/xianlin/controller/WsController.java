package vip.xianlin.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import vip.xianlin.entity.Result;

@Controller
public class WsController {
    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public Result<String> sendMessage(String chatMessage) {
        // 逻辑处理
        return Result.succ(chatMessage);
    }
}
