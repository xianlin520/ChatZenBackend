package top.chatzen.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import top.chatzen.filter.WSHeaderAuthFilter;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    
    @Resource
    WSHeaderAuthFilter wsHeaderAuthFilter;
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 订阅端点-服务器下发
        config.enableSimpleBroker("/topic", "/queue");
        // 消息端点-客户端上传
        config.setApplicationDestinationPrefixes("/app");
    }
    
    // 注册STOMP协议的节点，并映射指定的URL
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 添加一个服务端点，客户端就可以通过这个端点来进行连接。withSockJS()的作用是开启SockJS支持
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")  // 允许跨域
                .withSockJS();
        
    }
    
    /**
     * 拦截器方式2
     *
     * @param registration 注册器
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(wsHeaderAuthFilter);
    }
    
    
}
