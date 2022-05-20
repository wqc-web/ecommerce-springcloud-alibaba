package com.self.ecommerce.advice;

import com.self.ecommerce.annotation.IgnoreResponseAdvice;
import com.self.ecommerce.vo.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author wangqichao
 * @Description 实现统一响应
 */
@RestControllerAdvice(value = "com.self.ecommerce")
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 判断是否需要对响应进行处理
     */
    @SuppressWarnings("all")
    @Override
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        if(methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)){
            return false;
        }
        if(methodParameter.getMethod().isAnnotationPresent(IgnoreResponseAdvice.class)){
            return false;
        }
        return true;
    }


    @SuppressWarnings("all")
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter,
                                  MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // 页面返回数据对象
        CommonResponse<Object> commonResponse = new CommonResponse<>();
        commonResponse.setCode(200);
        // 传递返回对象
        if(o == null){
            return commonResponse;
        }else if(o instanceof CommonResponse){
            commonResponse = (CommonResponse<Object>) o;
        }else{
            commonResponse.setData(o);
        }
        return commonResponse;
    }
}
