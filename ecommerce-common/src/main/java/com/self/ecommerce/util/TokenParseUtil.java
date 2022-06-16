package com.self.ecommerce.util;

import com.alibaba.fastjson.JSON;
import com.self.ecommerce.constant.CommonConstant;
import com.self.ecommerce.vo.LoginUserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import sun.misc.BASE64Decoder;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;

/**
 * jwt token 解析工具类
 *
 * @author wangqichao
 */
public class TokenParseUtil {

    /**
     * 从jwt token 中解析 LoginUserInfo
     */
    public static LoginUserInfo parseUserInfoFromToken(String token) throws Exception {
        if (token == null) {
            return null;
        }
        Jws<Claims> claimsJws = parseToken(token, getPublicKey());
        Claims body = claimsJws.getBody();
        // 判断token 是否过期，过期就返回null
        if (body.getExpiration().before(Calendar.getInstance().getTime())) {
            return null;
        }
        // 返回 token 中保存的用户信息
        return JSON.parseObject(
                body.get(CommonConstant.JWT_USER_INFO_KEY).toString(), LoginUserInfo.class
        );
    }

    /**
     * 通过公钥解析 jwt token
     */
    private static Jws<Claims> parseToken(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }

    /**
     * 根据本地存储的公钥获取到 PublicKey 对象
     */
    private static PublicKey getPublicKey() throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
                new BASE64Decoder().decodeBuffer(CommonConstant.PUBLIC_KEY)
        );
        return KeyFactory.getInstance("RSA").generatePublic(keySpec);
    }

}
