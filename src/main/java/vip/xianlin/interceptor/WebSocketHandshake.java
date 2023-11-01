package vip.xianlin.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import vip.xianlin.utils.JwtUtils;

import java.util.Map;

@Slf4j
@Component
public class WebSocketHandshake implements HandshakeInterceptor {
    
    @Resource
    private JwtUtils jwtUtils;
    
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // 将ServerHttpRequest转换为ServletServerHttpRequest，以获取到Request域中的属性
        if (request instanceof ServletServerHttpRequest serverHttpRequest) {
            // 获取请求头中的Authorization
            String token = serverHttpRequest.getHeaders().getFirst("Authorization");
            // 进行token校验
            DecodedJWT decodedJWT = jwtUtils.resolveJwt(token);
            // 判断token是否有效
            if (decodedJWT == null) {
                log.info("握手失败-token无效");
                return false;
            }
            // 获取用户ID
            String userId = jwtUtils.toId(decodedJWT).toString();
            // 将用户ID放入attributes中
            attributes.put("userId", userId);
            log.info("握手通过, userId: {}", userId);
        }
        return true;
    }
    
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    
    }
}
