package vip.xianlin.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import vip.xianlin.handler.WebSocketHandler;
import vip.xianlin.interceptor.WebSocketHandshake;

@Configuration
@EnableWebSocket  // 开启websocket
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    
    
    @Bean
    public WebSocketHandler myHandler() {
        return new WebSocketHandler();
    }
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 启用一个简单的消息代理并配置一个或多个前缀来过滤目标以发送到该代理的消息
        config.enableSimpleBroker("/topic");
        // 配置一个或多个前缀以过滤从客户端发送到应用程序的消息
        config.setApplicationDestinationPrefixes("/app");
    }
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册一个STOMP协议的端点，并指定使用SockJS协议
        registry.addEndpoint("/chat")
                .setAllowedOrigins("*") // 允许所有的跨域请求
                .withSockJS() // 使用SockJS协议
                .setInterceptors(new WebSocketHandshake()); // 设置握手拦截器
    }
}

