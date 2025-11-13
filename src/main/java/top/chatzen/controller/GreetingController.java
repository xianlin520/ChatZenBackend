package top.chatzen.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import top.chatzen.model.HelloMessage;
import top.chatzen.model.Result;

import java.security.Principal;

@Controller
public class GreetingController {
    
    /**
     * 处理WebSocket消息，向所有订阅/topic/msg的用户发送欢迎消息
     * 
     * @param message WebSocket消息内容，包含用户名称
     * @param headerAccessor STOMP协议头信息，用于获取当前用户信息
     * @return 封装了当前用户信息的Result对象
     */
    @MessageMapping("/hello")
    @SendTo("/topic/msg")
    public Result<Principal> greeting(HelloMessage message, StompHeaderAccessor headerAccessor) {
        Principal user = headerAccessor.getUser();
        return Result.succ(200, "Hello, " + message.getName(), user);
    }
}