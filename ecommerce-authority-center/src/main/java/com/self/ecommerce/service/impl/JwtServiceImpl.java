package com.self.ecommerce.service.impl;

import com.alibaba.fastjson.JSON;
import com.self.ecommerce.constant.AuthorityConstant;
import com.self.ecommerce.constant.CommonConstant;
import com.self.ecommerce.dao.EcommerceUserDao;
import com.self.ecommerce.entity.EcommerceUser;
import com.self.ecommerce.service.IJwtService;
import com.self.ecommerce.vo.LoginUserInfo;
import com.self.ecommerce.vo.UsernameAndPasswordVO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

/**
 * Jwt 服务实现
 *
 * @author wangqichao
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class JwtServiceImpl implements IJwtService {

    @Autowired
    EcommerceUserDao ecommerceUserDao;

    @Override
    public String generateToken(String username, String password) throws Exception {
        return generateToken(username, password, 0);
    }

    @Override
    public String generateToken(String username, String password, int expire) throws Exception {
        EcommerceUser ecommerceUser = ecommerceUserDao.findByUsernameAndPassword(username, password);
        if (ecommerceUser == null) {
            log.error("can not find user : [{}] [{}]", username, password);
            return null;
        }
        // token 存入的对象， jwt中存储的信息
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        loginUserInfo.setId(ecommerceUser.getId());
        loginUserInfo.setUsername(ecommerceUser.getUsername());
        // 时间: 天
        if (expire <= 0) {
            expire = AuthorityConstant.DEFAULT_EXPIRE_DAY;
        }
        // 计算过期时间
        ZonedDateTime zdt = LocalDate.now().plus(expire, ChronoUnit.DAYS).atStartOfDay(ZoneId.systemDefault());
        Date expireDate = Date.from(zdt.toInstant());
        // 生成 jwt
        return Jwts.builder()
                // jwt payload --> KV
                .claim(CommonConstant.JWT_USER_INFO_KEY, JSON.toJSONString(loginUserInfo))
                // jwt id
                .setId(UUID.randomUUID().toString())
                // jwt 过期时间
                .setExpiration(expireDate)
                // jwt 签名 --> 加密
                .signWith(getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }

    @Override
    public String registerUserAndGenerateToken(UsernameAndPasswordVO usernameAndPasswordVO) throws Exception {
        // 判断用户是否重复注册
        EcommerceUser ecommerceUser = ecommerceUserDao.findByUsername(usernameAndPasswordVO.getUsername());
        if (ecommerceUser != null) {
            log.error("user by username is register exist : [{}]", ecommerceUser.getUsername());
            return null;
        }
        //
        ecommerceUser = new EcommerceUser();
        ecommerceUser.setUsername(usernameAndPasswordVO.getUsername());
        ecommerceUser.setPassword(usernameAndPasswordVO.getPassword());
        ecommerceUser.setExtraInfo("{}");
        // db insert
        ecommerceUser = ecommerceUserDao.save(ecommerceUser);
        // print user message
        log.info("register user success : [{}]", JSON.toJSONString(ecommerceUser));
        // 生成token
        return generateToken(ecommerceUser.getUsername(), ecommerceUser.getPassword());
    }

    private PrivateKey getPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                new BASE64Decoder().decodeBuffer(AuthorityConstant.PRIVATE_KEY));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }

}
