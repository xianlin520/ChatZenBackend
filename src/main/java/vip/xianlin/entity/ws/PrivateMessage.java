package vip.xianlin.entity.ws;

import lombok.Data;

@Data
public class PrivateMessage {
    private String content;
    private String recipient;
}
