package vip.xianlin.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xianlin.entity.vo.request.MessageVo;
import vip.xianlin.utils.JwtUtils;

@RestController
@RequestMapping("friend")
@Tag(name = "好友相关接口", description = "好友分组, 请求, 发送消息等操作")
public class FriendChatController {
    
    @Resource
    JwtUtils utils;
    
    @PostMapping("/send-message")
    public void sendChat(@RequestBody MessageVo vo, HttpServletRequest request) {
        // 获取Attribute中的用户ID
        Integer userId = (Integer) request.getAttribute("userId");
        // 将消息发送给好友
//        WebSocketHandler.sendMsgToUserId(vo.getFriendId(), Result.succ(vo.getMessage()));
        
        
    }
}
