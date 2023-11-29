package vip.xianlin.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration  // 标记这是一个配置类
@EnableWebSocketMessageBroker  // 开启WebSocket消息代理
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    
    // 注册STOMP协议的节点，并映射指定的URL
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 添加一个服务端点，客户端就可以通过这个端点来进行连接。withSockJS()的作用是开启SockJS支持
        registry.addEndpoint("/ws").withSockJS();
    }
    
    // 配置消息代理
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 启用一个简单的消息代理，并配置一个或多个前缀来过滤针对代理的目的地
        registry.enableSimpleBroker("/topic", "/queue", "/user");
        // 配置一个或多个前缀，用于过滤针对应用程序的目的地。即客户端发送的目的地需要以“/app”开头
        registry.setApplicationDestinationPrefixes("/app");
        // 配置用户目的地的前缀，用于将用户目的地的消息发送到消息代理
        registry.setUserDestinationPrefix("/user");
    }
}

