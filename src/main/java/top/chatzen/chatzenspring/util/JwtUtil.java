package top.chatzen.chatzenspring.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY = "your_secret_key";
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
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
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
