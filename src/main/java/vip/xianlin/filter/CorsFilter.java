package vip.xianlin.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import vip.xianlin.utils.Const;

import java.io.IOException;


@Component // 配置跨域过滤器
@Order(Const.ORDER_CORS) // 配置优先级
public class CorsFilter extends HttpFilter {
    
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 设置跨域请求头
        this.addCorsHeader(request, response);
        chain.doFilter(request, response);
        
    }
    
    private void addCorsHeader(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,PUT");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }
    
}


