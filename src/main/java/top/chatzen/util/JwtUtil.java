package top.chatzen.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    // 从配置文件读取key
    @Value("${chat-zen.jwt.key}")
    private String SECRET_KEY;
    
    // 设置过期时间
    @Getter
    @Value("${chat-zen.jwt.expiration}")
    private long EXPIRATION_TIME;
    
    private Algorithm algorithm;
    
    @PostConstruct
    public void init(){
        this.algorithm = Algorithm.HMAC256(SECRET_KEY);
    }
    
    /**
     * 生成JWT Token
     * @param userId 用户ID
     * @return 生成的JWT Token
     */
    public String generateToken(String userId) {
        return JWT.create()
                .withSubject(userId)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }
    
    /**
     * 提取用户ID
     * @param token JWT Token
     * @return 用户ID
     */
    public String extractUserId(String token) {
        return verifyToken(token).getSubject();
    }
    
    /**
     * 检查Token是否过期
     * @param token JWT Token
     * @return 是否过期 true表示过期，false表示未过期
     */
    public boolean isTokenExpired(String token) {
        return verifyToken(token).getExpiresAt().before(new Date());
    }
    
    /**
     * 解析并验证JWT Token
     * @param token JWT Token
     * @return 解码后的JWT
     */
    private DecodedJWT verifyToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }
    
   
}
