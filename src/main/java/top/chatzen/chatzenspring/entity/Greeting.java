package top.chatzen.chatzenspring.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Greeting {
    
    private String content;
    
    public Greeting() {
    }
    
    public Greeting(String content) {
        this.content = content;
    }
    
}
