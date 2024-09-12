package top.chatzen.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.LinkedTransferQueue;

public class ChatWebSocketHandler extends TextWebSocketHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);
    
    private static final LinkedTransferQueue<WebSocketSession> connections = new LinkedTransferQueue<>();
    
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 将所有连接的连接添加到列表中
        connections.add(session);
        
        // 获取列表数量
        int count = connections.size();
        String sessionId = session.getId();
        String uri = session.getUri().toString();
        InetSocketAddress remoteAddress = session.getRemoteAddress();
        
        logger.info("与会话ID {} 建立连接", sessionId);
        logger.info("请求URI: {}", uri);
        logger.info("远程地址: {}", remoteAddress);
        
        session.sendMessage(new TextMessage("已成功建议连接: " + remoteAddress + " 当前连接数: " + count));
        
    }
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 处理收到的消息
        String payload = message.getPayload();
        logger.info("从会话ID {} 收到消息: {}", session.getId(), payload);
        
        // 将收到的消息， 发送到列表所有连接中
        for (WebSocketSession connection : connections) {
            if (connection.isOpen())
                connection.sendMessage(new TextMessage("广播消息: " + payload));
        }
        
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 从列表中移除连接
        connections.remove(session);
        // 在连接关闭后做些什么
        logger.info("与会话ID {} 的连接关闭，状态: {}", session.getId(), status);
    }
}
