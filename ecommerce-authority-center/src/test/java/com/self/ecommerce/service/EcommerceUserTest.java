package com.self.ecommerce.service;

import cn.hutool.crypto.digest.MD5;
import com.alibaba.fastjson.JSON;
import com.self.ecommerce.dao.EcommerceUserDao;
import com.self.ecommerce.entity.EcommerceUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class EcommerceUserTest {

    @Autowired
    EcommerceUserDao ecommerceUserDao;

    @Test
    public void createUserRecord(){
        EcommerceUser ecommerceUser = new EcommerceUser();
        ecommerceUser.setUsername(System.currentTimeMillis()+"");
        ecommerceUser.setPassword(MD5.create().digestHex("123456"));
        ecommerceUser.setExtraInfo("{}");
        log.info("save user: {}", JSON.toJSON(ecommerceUserDao.save(ecommerceUser)));
    }

}
