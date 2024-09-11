package top.chatzen.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class JwtUtil {
    // 从配置文件读取key
    @Value("${chat-zen.jwt.key}")
    private static String SECRET_KEY;
    
    // 设置过期时间
    @Value("${chat-zen.jwt.expiration}")
    private static long EXPIRATION_TIME;
    
    // 设置算法
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
    
    /**
     * 生成JWT Token
     * @param userId 用户ID
     * @return 生成的JWT Token
     */
    public static String generateToken(String userId) {
        return JWT.create()
                .withSubject(userId)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }
    
    /**
     * 解析并验证JWT Token
     * @param token JWT Token
     * @return 解码后的JWT
     */
    public static DecodedJWT verifyToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }
    
    /**
     * 提取用户ID
     * @param token JWT Token
     * @return 用户ID
     */
    public static String extractUserId(String token) {
        return verifyToken(token).getSubject();
    }
    
    /**
     * 检查Token是否过期
     * @param token JWT Token
     * @return 是否过期
     */
    public static boolean isTokenExpired(String token) {
        return verifyToken(token).getExpiresAt().before(new Date());
    }
}
