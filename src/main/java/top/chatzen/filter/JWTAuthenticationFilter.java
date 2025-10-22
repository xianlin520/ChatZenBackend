package top.chatzen.filter;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import top.chatzen.config.Config;
import top.chatzen.constant.Const;
import top.chatzen.model.Result;
import top.chatzen.model.SecurityUser;
import top.chatzen.util.JwtUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    
    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
    private final AntPathMatcher pathMatcher = new AntPathMatcher(); // 路径匹配器
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private Config.Security security; // 获取配置文件中的白名单
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 从请求头中获取Authorization信息
        String header = request.getHeader("Authorization");
        
        // 判断是否是放行请求
        if (isFilterRequest(request)) {
            // 如果是放行请求，则直接放行
            logger.info("放行路径 {}", request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }
        logger.info("拦截路径 {}", request.getRequestURI());
        
        
        // 判断是否以Bearer开头
        if (header.length() < 8 || !header.startsWith("Bearer ")) {
            logger.error("Token格式错误");
            fallback("Token格式错误, 请重新获取", response);
            return;
        }
        
        // 获取jwt
        String token = header.substring(7);
        try {
            // 验证JWTToken是否有效并在有效期
            boolean tokenExpired = jwtUtil.isTokenExpired(token);
            if (tokenExpired) {
                // 验证失败
                logger.error("Token已过期");
                fallback("Token失效, 请重新登录", response);
                return;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            fallback("Token解析失败, 请重新获取", response);
            return;
        }
        // Token合法性验证成功
        String userId = jwtUtil.extractUserId(token);
        String cache = (String) redisTemplate.opsForValue().get(String.format(Const.REDIS_KEY, userId));
        SecurityUser securityUser = JSON.parseObject(cache, SecurityUser.class);
        if (securityUser == null) {
            fallback("用户信息解析失败, 请重新登录", response);
            return;
        }
        // 验证当前Token是否与数据库的Token一致
        if (!Objects.equals(securityUser.getJwtToken(), token)) {
            fallback("Token已更新, 请获取最新Token", response);
            return;
        }
        logger.info("用户{}验证成功", securityUser.getUserAccount().getUsername());
        // 将用户信息存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(securityUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 放行
        filterChain.doFilter(request, response);
    }
    
    /**
     * 当请求不符合要求时，执行此方法
     *
     * @param message  错误信息
     * @param response HttpServletResponse对象，用于设置响应内容
     */
    private void fallback(String message, HttpServletResponse response) {
        logger.error("token验证失败{}", message);
        // 设置响应的字符编码和内容类型
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter writer = null;
        try {
            // 如果message为空，则设置默认错误信息
            if (message == null) {
                message = "403 Forbidden";
            }
            // 创建一个Result对象，表示请求失败，状态码为403，错误信息为message
            Result<String> res = Result.fail(403, message, null);
            // 将Result对象转换为JSON字符串，并写入响应中
            writer = response.getWriter();
            writer.append(JSON.toJSONString(res));
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            // 关闭PrintWriter对象
            if (writer != null) {
                writer.close();
            }
        }
    }
    
    
    /**
     * 判断当前请求是否需要过滤
     *
     * @param request 请求的Request
     * @return 如果请求的URI在白名单中，则返回false，否则返回true
     */
    private boolean isFilterRequest(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String filterPath = request.getRequestURI();
        List<String> permitAllPathList = security.getPermitAllPath();
        if (CollectionUtils.isEmpty(permitAllPathList)) {
            return false;
        }
        for (String path : permitAllPathList) {
            String pattern = contextPath + path;
            pattern = pattern.replaceAll("/+", "/");
            if (pathMatcher.match(pattern, filterPath)) {
                return true;
            }
        }
        return false;
    }
}
