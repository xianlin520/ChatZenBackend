package top.chatzen.handler;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import top.chatzen.model.Result;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        fallback(authException.getMessage(), response);
    }
    
    private void fallback(String message, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try (PrintWriter writer = response.getWriter()) {
            if (message == null) {
                message = "认证失败！";
            }
            Result<String> res = Result.fail(HttpServletResponse.SC_UNAUTHORIZED, message, null);
            writer.append(JSON.toJSONString(res));
        } catch (IOException e) {
            log.error("响应写入失败: {}", e.getMessage());
        }
    }
}
