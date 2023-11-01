package vip.xianlin.config;


import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import vip.xianlin.handler.WebSocketHandler;
import vip.xianlin.interceptor.WebSocketHandshake;

@Configuration
@EnableWebSocket  // 开启websocket
public class WebSocketConfig implements WebSocketConfigurer {
    
    
    // 注入拦截器
    @Resource
    private WebSocketHandshake myWebSocketHandshake;
    
    @Bean
    public WebSocketHandler myHandler() {
        return new WebSocketHandler();
    }
    
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 注册拦截器
        registry.addHandler(myHandler(), "/websocket")
                .addInterceptors(myWebSocketHandshake);
    }
}

