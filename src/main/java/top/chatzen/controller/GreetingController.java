package top.chatzen.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import top.chatzen.entity.Greeting;
import top.chatzen.entity.HelloMessage;

@Controller
public class GreetingController {
    
    @MessageMapping("/hello")
    @SendTo("/topic/msg")
    public Greeting greeting(HelloMessage message) throws Exception {
        return new Greeting("Hello, " + message.getName() + "!");
    }
}
