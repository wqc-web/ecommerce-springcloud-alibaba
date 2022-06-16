package com.self.ecommerce.service;

import cn.hutool.core.codec.Base64;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * RSA 非对称加密：生成公钥和私钥
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class RSATest {

    @Test
    public void generateKeyBytes() throws NoSuchAlgorithmException {
        // 初始化
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        // 生成公钥和私钥
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey  = (RSAPrivateKey) keyPair.getPrivate();
        log.info("public key : {}" , Base64.encode(publicKey.getEncoded()));
        log.info("private key : {}", Base64.encode(privateKey.getEncoded()));
    }

}
