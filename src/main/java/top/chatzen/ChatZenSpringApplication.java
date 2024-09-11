package top.chatzen;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("top.chatzen.dao")
public class ChatZenSpringApplication {
    
    private static final Logger log = LoggerFactory.getLogger(ChatZenSpringApplication.class);
    
    public static void main(String[] args) {
        SpringApplication.run(ChatZenSpringApplication.class, args);
        log.info("项目启动成功 === SpringBoot3.3.3 - JDK 21");
    }
    
}
