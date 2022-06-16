package com.self.ecommerce.service;

import com.alibaba.fastjson.JSON;
import com.self.ecommerce.service.impl.JwtServiceImpl;
import com.self.ecommerce.util.TokenParseUtil;
import com.self.ecommerce.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class JWTServiceTest {

    @Autowired
    JwtServiceImpl jwtService;

    @Test
    public void testGenerateAndParseToken() throws Exception {
        // 生成jwt token
        String token = jwtService.generateToken(
                "wqc", "e10adc3949ba59abbe56e057f20f883e"
        );
        log.info("generate token : {}", token);
        // 解析jwt token
        LoginUserInfo loginUserInfo = TokenParseUtil.parseUserInfoFromToken(token);
        log.info("parse token : {}", JSON.toJSONString(loginUserInfo));
    }

}
