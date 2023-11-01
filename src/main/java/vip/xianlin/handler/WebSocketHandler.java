package vip.xianlin.handler;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import vip.xianlin.entity.Result;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {
    
    // 保存所有在线用户的session
    private static final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();
    
    /**
     * 封装消息
     *
     * @param result JSON消息
     * @return TextMessage 消息对象
     */
    private static TextMessage myTextMessage(Result result) {
        return new TextMessage(result.asJsonString());
    }
    
    public static WebSocketSession getSessionByUserId(String userId) {
        return userSessions.get(userId);
    }
    
    public static void sendMsgToUserId(String userId, Result result) {
        try {
            userSessions.get(userId).sendMessage(myTextMessage(result));
        } catch (Exception e) {
            log.error("发送消息失败: {}", e.getMessage());
        }
    }
    
    // 当WebSocket连接建立时触发此方法
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 获取userId
        String userId = (String) session.getAttributes().get("userId");
        
        // 判断用户是否已经连接
        if (userSessions.containsKey(userId)) {
            // 向旧链接发送消息
            userSessions.get(userId).sendMessage(myTextMessage(Result.loginExpire("您的账号在其他地方登录")));
            // 关闭旧的连接
            userSessions.get(userId).close();
        }
        
        // 发送JSON消息给客户端
        session.sendMessage(myTextMessage(Result.succ("连接成功")));
        // 将用户ID和session绑定
        userSessions.put(userId, session);
        // 打印在线用户
        log.info("用户{}上线, 当前在线人数: {}", userId, userSessions.size());
    }
    
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 判断消息是否为JSON, 使用ali的fastjson
        if (!JSON.isValid(message.getPayload())) {
            log.info("非法消息: {}", message.getPayload());
            session.sendMessage(myTextMessage(Result.fail("非法消息")));
            return;
        }
        // 当收到文本消息时触发此方法
        log.info("Received message: {}", message.getPayload());
        
        // 发送消息给客户端
        session.sendMessage(myTextMessage(Result.succ("收到消息")));
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 当WebSocket连接关闭时触发此方法
        log.info("Disconnected: {}", session.getId());
        // 获取userId, 并移除
        String userId = (String) session.getAttributes().get("userId");
        userSessions.remove(userId);
    }
    
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // 当发生错误时触发此方法
        log.error("Error: {}", exception.getMessage());
        // 发送错误消息给客户端
        session.sendMessage(myTextMessage(Result.fail(exception.getMessage())));
        // 关闭连接
        session.close();
        // 获取userId, 并移除
        String userId = (String) session.getAttributes().get("userId");
        userSessions.remove(userId);
    }
}
