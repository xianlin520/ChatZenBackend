package vip.xianlin.controller;

import com.alibaba.fastjson2.JSONObject;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import vip.xianlin.entity.Result;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/websocket/{userid}")
@Controller
@Slf4j
public class WebSocketServer {
    
    /**
     * 记录当前在线连接数
     */
    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();
    
    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userid") String userId) {
        sessionMap.put(userId, session);
        log.info("有新用户加入，username={}, 当前在线人数为：{}", userId, sessionMap.size());
        for (Object key : sessionMap.keySet()) {
            JSONObject jsonObject = new JSONObject();
        }
//        {"users": [{"username": "zhang"},{ "username": "admin"}]}
        sendMessage(Result.succ(200, "连接成功", null).asJsonString(), session);  // 后台发送消息给所有的客户端
    }
    
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("userid") String userId) {
        sessionMap.remove(userId);
        log.info("有一连接关闭，移除username={}的用户session, 当前在线人数为：{}", userId, sessionMap.size());
    }
    
    @OnError
    public void onError(Session session, Throwable error) {
        sessionMap.remove(session.getId());
        log.error("websocket发生错误");
    }
    
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("服务端收到客户端[{}]的消息{}", session.getId(), message);
        sendMessage(message, session);
    }
    
    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message, Session toSession) {
        try {
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }
    
}
