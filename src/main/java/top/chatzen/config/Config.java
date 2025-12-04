package top.chatzen.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration //配置类
@EnableConfigurationProperties(Config.Security.class) //启用配置属性
public class Config {
    //配置类，用于配置一些全局的配置信息
    @Setter
    @Getter
    @ConfigurationProperties(prefix = "chat-zen.security")
    public static class Security {
        private List<String> permitAllPath;
        
    }
}
