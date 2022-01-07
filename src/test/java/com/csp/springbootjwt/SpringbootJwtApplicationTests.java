package com.csp.springbootjwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.HashMap;

@SpringBootTest
class SpringbootJwtApplicationTests {

    @Test
    void contextLoads() {
//        HashMap<String,Object> map = new HashMap<>();
//
//        Calendar instance = Calendar.getInstance();
//        instance.add(Calendar.SECOND,200);
//
//        String token = JWT.create()
//                .withHeader(map)     //Header
//                .withClaim("userid", 21) //Payload
//                .withClaim("username", "Victor")
//                .withExpiresAt(instance.getTime())       //过期时间设置
//                .sign(Algorithm.HMAC256("!@#$%QWER"));//签名
//
//        //打印token
//        System.out.println(token);

    }

    @Test
    void testsign(){
        //创建验证对象
//        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("!@#$%QWER")).build();
//
//        DecodedJWT verify = jwtVerifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NDE0NTMyNTksInVzZXJpZCI6MjEsInVzZXJuYW1lIjoiVmljdG9yIn0.2EVJL_Do-h8XB8Tqixsfk80wWbatRSGsxha_j4EHno0");
//
//        System.out.println(verify.getClaims().get("username").asString());
//        System.out.println(verify.getExpiresAt());
    }



}
