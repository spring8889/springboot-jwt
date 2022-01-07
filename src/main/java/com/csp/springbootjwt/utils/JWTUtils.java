package com.csp.springbootjwt.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Calendar;
import java.util.Map;

public class JWTUtils {
    //签名密钥
    private static final String SIGNKEY = "!@#$%QWER123456121234521";

    /**
     * 生成token
     * header.payload.signature
     */
    public static String getToken(Map<String,String> map){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE,15);

        //创建JWTBuilder
        JWTCreator.Builder builder = JWT.create();

        //Payload配置
        map.forEach((k,v)->{
            builder.withClaim(k, v);
        });


        String token = builder.withExpiresAt(instance.getTime())       //过期时间设置
                .sign(Algorithm.HMAC256(SIGNKEY));//签名

        return token;
    }

    /**
     * 验证 token 合法性
     */
    public static DecodedJWT verifyToken(String token){
        //验证合法性
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGNKEY)).build().verify(token);
        return verify;
    }

}
