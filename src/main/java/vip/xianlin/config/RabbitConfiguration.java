package vip.xianlin.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vip.xianlin.utils.Const;

@Configuration
public class RabbitConfiguration {
    
    // 邮件队列, 消费者
    @Bean("mailQueue")
    public Queue mailQueue() {
        return new Queue(Const.MQ_MAIL);
    }
}
