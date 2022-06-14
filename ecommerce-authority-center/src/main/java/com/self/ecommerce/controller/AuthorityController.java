package com.self.ecommerce.controller;

import com.alibaba.fastjson.JSON;
import com.self.ecommerce.annotation.IgnoreResponseAdvice;
import com.self.ecommerce.service.impl.JwtServiceImpl;
import com.self.ecommerce.vo.JwtToken;
import com.self.ecommerce.vo.UsernameAndPasswordVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangqichao
 */
@Slf4j
@RestController
@RequestMapping("/authority")
public class AuthorityController {

    @Autowired
    JwtServiceImpl jwtService;


    /**
     * 授权中心获取token
     */
    @IgnoreResponseAdvice
    @PostMapping("/token")
    public JwtToken token(@RequestBody UsernameAndPasswordVO usernameAndPasswordVO) throws Exception {
        log.info("request to get token with param : [{}]", JSON.toJSONString(usernameAndPasswordVO));
        return new JwtToken(jwtService.generateToken(
                usernameAndPasswordVO.getUsername(), usernameAndPasswordVO.getPassword()
        ));
    }

    /**
     * 注册用户并返回注册用户的token
     */
    @IgnoreResponseAdvice
    @PostMapping("/register")
    public JwtToken register(@RequestBody UsernameAndPasswordVO usernameAndPasswordVO) throws Exception {
        log.info("register user with param : [{}]", JSON.toJSONString(usernameAndPasswordVO));
        return new JwtToken(jwtService.registerUserAndGenerateToken(
                usernameAndPasswordVO
        ));
    }

}
