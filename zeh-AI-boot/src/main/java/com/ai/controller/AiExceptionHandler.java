package com.ai.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * AI Controller 的统一参数异常处理器。
 * 将 JSON 字段校验、方法参数校验和显式业务参数异常统一转换为 RFC 7807 Problem Detail。
 */
@RestControllerAdvice(basePackages = "com.ai.controller")
public class AiExceptionHandler {

    /**
     * 把可归因于客户端输入的异常映射为 HTTP 400。
     *
     * @param exception Bean Validation 或业务参数校验抛出的异常
     * @return 可被 Spring MVC 序列化为 application/problem+json 的错误对象
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class,
            IllegalArgumentException.class})
    public ProblemDetail invalidRequest(Exception exception) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, messageOf(exception));
        problem.setTitle("AI请求参数不合法");
        return problem;
    }

    /**
     * 优先提取请求体字段的精确校验消息，其他异常直接使用其 message。
     *
     * @param exception 待解析异常
     * @return 面向调用方的错误详情
     */
    private String messageOf(Exception exception) {
        if (exception instanceof MethodArgumentNotValidException validationException
                && validationException.getBindingResult().getFieldError() != null) {
            return validationException.getBindingResult().getFieldError().getDefaultMessage();
        }
        return exception.getMessage();
    }
}
