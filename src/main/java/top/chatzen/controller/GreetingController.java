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
    
    @MessageMapping("/hello")
    @SendTo("/topic/msg")
    public Result<Principal> greeting(HelloMessage message, StompHeaderAccessor headerAccessor) {
        Principal user = headerAccessor.getUser();
        return Result.succ(200, "Hello, " + message.getName(), user);
    }
}
