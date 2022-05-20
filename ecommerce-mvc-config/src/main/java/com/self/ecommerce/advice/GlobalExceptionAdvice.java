package com.self.ecommerce.advice;

import com.self.ecommerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangqichao
 * @Description 统一异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    public CommonResponse<Object> handlerCommerceException(HttpServletRequest request, Exception e) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();
        commonResponse.setCode(500);
        commonResponse.setMsg(e.getMessage());
        log.error("ecommerce service has error : [{}]", e.getMessage(), e);
        return commonResponse;
    }

}
