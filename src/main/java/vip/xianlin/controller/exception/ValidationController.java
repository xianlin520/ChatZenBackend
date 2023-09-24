package vip.xianlin.controller.exception;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vip.xianlin.entity.Result;

@RestControllerAdvice
@Slf4j
public class ValidationController {
    @ExceptionHandler(ValidationException.class)
    public Result validateError(ValidationException exception) {
        log.warn("Resolved [{}: {}]", exception.getClass().getName(), exception.getMessage());
        return Result.fail("请求参数有误");
    }
}
