package vip.xianlin.service;

import vip.xianlin.entity.vo.request.MessageVo;

public interface IWebSocketService {
    
    /**
     * 发送消息给指定用户
     *
     * @param msgVo 发送消息对象
     */
    void sendMsgToUserId(MessageVo msgVo, Integer userId);
}
