package top.chatzen.chatzenspring.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import top.chatzen.chatzenspring.entity.Greeting;
import top.chatzen.chatzenspring.entity.HelloMessage;

@Controller
public class GreetingController {
    
    @MessageMapping("/hello")
    @SendTo("/topic/msg")
    public Greeting greeting(HelloMessage message) throws Exception {
        return new Greeting("Hello, " + message.getName() + "!");
    }
}
