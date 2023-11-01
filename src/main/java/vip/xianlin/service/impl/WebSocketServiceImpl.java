package vip.xianlin.service.impl;

import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import vip.xianlin.entity.vo.request.MessageVo;
import vip.xianlin.service.IWebSocketService;
import vip.xianlin.utils.Const;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * WebSocket 服务实现类
 * </p>
 *
 * @author XianLin
 * @since 2023-09-25
 */
public class WebSocketServiceImpl implements IWebSocketService {
    
    // 消息队列
    @Resource
    RabbitTemplate rabbitTemplate;
    
    @Override
    public void sendMsgToUserId(MessageVo msgVo, Integer userId) {
        // 创建map
        Map<String, Object> map = new HashMap<>();
        // 将消息对象放入map
        map.put("msgVo", msgVo);
        // 将用户ID放入map
        map.put("userId", userId);
        // 发送消息到消息队列
        rabbitTemplate.convertAndSend(Const.MQ_WS_SEND, map);
    }
}
