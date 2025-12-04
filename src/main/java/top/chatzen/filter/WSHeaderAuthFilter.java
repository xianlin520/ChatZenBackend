package top.chatzen.filter;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import top.chatzen.constant.Const;
import top.chatzen.model.RedisUserDto;
import top.chatzen.model.SecurityUser;
import top.chatzen.util.JwtUtil;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
/*
  websocket拦截器
  在WebSocket连接建立前进行拦截，验证token
  从请求头获取鉴权, 并设置user信息
 */
public class WSHeaderAuthFilter implements ChannelInterceptor {
    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private JwtUtil jwtUtil;
    
    /**
     * 连接前监听
     *
     * @param message 消息
     * @param channel 通道
     * @return 消息, 返回null表示为建立连接
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        //1、判断是否首次连接
        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            //2、判断token
            List<String> nativeHeader = accessor.getNativeHeader(AUTH_HEADER);
            if (nativeHeader != null) {
                String header = nativeHeader.getFirst();
                log.info("header: {}", header);
                // 判断是否以Bearer开头
                if (header == null || header.length() < 8 || !header.startsWith(BEARER_PREFIX)) {
                    log.error("Token格式错误");
                    return null;
                }
                String token = header.substring(7);
                try {
                    // 验证JWTToken是否有效并在有效期
                    boolean tokenExpired = jwtUtil.isTokenExpired(token);
                    if (tokenExpired) {
                        // 验证失败
                        log.error("Token已过期");
                        return null; // 返回null表示拒绝连接
                    }
                } catch (Exception e) {
                    log.error("Token解析失败", e);
                    return null; // 返回null表示拒绝连接
                }
                // Token合法性验证成功
                String userId = jwtUtil.extractUserId(token);
                Object cacheObj = redisTemplate.opsForValue().get(String.format(Const.REDIS_KEY, userId));
                if (cacheObj == null) {
                    log.error("用户缓存不存在，用户ID: {}", userId);
                    return null; // 返回null表示拒绝连接
                }

                // 现在只处理RedisUserDto类型
                if (!(cacheObj instanceof RedisUserDto)) {
                    log.error("用户缓存数据格式错误，用户ID: {}", userId);
                    return null; // 返回null表示拒绝连接
                }

                RedisUserDto redisUserDto = (RedisUserDto) cacheObj;
                SecurityUser securityUser = redisUserDto.toSecurityUser();

                if (securityUser == null) {
                    log.error("用户信息解析失败，用户ID: {}", userId);
                    return null; // 返回null表示拒绝连接
                }

                // 验证当前Token是否与存储的Token一致
                if (!Objects.equals(redisUserDto.getJwtToken(), token)) {
                    log.error("Token不匹配，用户ID: {}", userId);
                    return null; // 返回null表示拒绝连接
                }
                log.info("用户{}验证成功", securityUser.getUserAccount().getAccount());
                // 将用户名赋值
                final String principalName = securityUser.getUsername();
                Principal principal = () -> principalName;
                accessor.setUser(principal);
                return message;
            }
            return null;
        }
        //不是首次连接，已经登陆成功
        return message;
    }
}
