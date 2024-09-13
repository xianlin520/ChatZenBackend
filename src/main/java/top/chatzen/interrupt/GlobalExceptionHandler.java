package top.chatzen.interrupt;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.chatzen.model.Result;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    
    // 处理AuthenticationException
    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    public Result<String> authenticationExceptionHandler(HttpServletRequest req, AuthenticationException e) {
        log.error("发生认证异常！原因是：{}", e.getMessage());
        return Result.fail(e.getMessage());
    }
    
    // 处理BizException
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public Result<String> bizExceptionHandler(HttpServletRequest req, BizException e) {
        log.error("发生业务异常！原因是：{}", e.getErrorMsg());
        return Result.fail(e.getErrorMsg());
    }
    
    
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public Result<String> exceptionHandler(HttpServletRequest req, NullPointerException e) {
        log.error("发生空指针异常！原因是:", e);
        return Result.fail("空指针异常");
    }
    
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<String> exceptionHandler(HttpServletRequest req, Exception e) {
        log.error("未知异常！原因是:", e);
        return Result.fail("未知异常");
    }
}
