package vip.xianlin.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import vip.xianlin.entity.Result;
import vip.xianlin.entity.vo.request.MessageVo;
import vip.xianlin.handler.WebSocketHandler;
import vip.xianlin.utils.Const;

import java.util.Map;

// 邮件队列监听器, 消费者
@Component
@Slf4j
@RabbitListener(queues = Const.MQ_WS_SEND)
public class WsSendMsgQueueListener {
    @RabbitListener
    public void sendMailMessage(Map<String, Object> map) {
        // 获取消息对象
        MessageVo msgVo = (MessageVo) map.get("msgVo");
        // 获取用户ID
        Integer userId = (Integer) map.get("userId");
        // 获取用户session
        WebSocketSession session = WebSocketHandler.getSessionByUserId(msgVo.getFriendId());
        // 判断用户是否在线
        if (session != null) {
            // 发送消息
            WebSocketHandler.sendMsgToUserId(userId.toString(), Result.succ(msgVo.getMessage()));
        }
    }
}
