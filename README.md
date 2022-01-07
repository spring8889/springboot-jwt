JSON Web Token 用于保证Java web前后端分离的系统或者是跨系统间以JSON方式建立一种基于令牌的安全机制。本软件采用一套极简的方式实现了JWT的登录认证及授权机制的实现。
1、引入依赖
复制代码
        <!--引入jwt-->
        <dependency>
            <groupId>com.auth0</groupId>
            <artifactId>java-jwt</artifactId>
            <version>3.18.2</version>
        </dependency>
复制代码
2、创建JWTUtils工具类
复制代码
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

        String token = builder.withExpiresAt(instance.getTime())   //过期时间设置
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
复制代码
 3、创建系统拦截处理类
复制代码
package com.csp.springbootjwt.config;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.csp.springbootjwt.utils.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Map<String,Object> map = new HashMap<>();

        //获取请求头中的令牌
        String token = request.getHeader("token");

        try {
            //验证令牌
            JWTUtils.verifyToken(token);

            //验证通过，放行请求
            return true;
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            map.put("msg", "无效签名");
        }catch (TokenExpiredException e){
            e.printStackTrace();
            map.put("msg", "token已过期");
        }catch (AlgorithmMismatchException e){
            e.printStackTrace();
            map.put("msg", "token算法不一致");
        }catch (Exception e){
            e.printStackTrace();
            map.put("msg", "token无效");
        }
        //设置状态
        map.put("state",false);
        //采用jackson方式将map转化为json 字符串
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
复制代码
4、创建系统拦截器
复制代码
package com.csp.springbootjwt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
        .addPathPatterns("/**")
        //.excludePathPatterns("/user/**");
        //.addPathPatterns("/test1")
        .excludePathPatterns("/login");
        //.excludePathPatterns("/**");
    }
}
复制代码
5、登录验证及业务处理
复制代码
package com.csp.springbootjwt.Controller;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.csp.springbootjwt.entity.User;
import com.csp.springbootjwt.service.UserService;
import com.csp.springbootjwt.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public Map<String, Object> login(User user){
        Map<String,Object> map = new HashMap<>();
        System.out.println(user);

        try{
            User userDB = userService.login(user);
            map.put("state",true);
            map.put("msg","认证成功");

            //返回Token
            Map<String,String> payloadMap = new HashMap<>();
            payloadMap.put("id",new Integer(userDB.getId()).toString());
            payloadMap.put("username",userDB.getUsername());
            String token = JWTUtils.getToken(payloadMap);
            map.put("token",token);
        }
        catch (Exception e){
            map.put("state",false);
            map.put("msg", e.getMessage());
        }
        return map;
    }

    @PostMapping("/test1")
    public Map<String,Object> test(HttpServletRequest request){
        String token = request.getHeader("token");
        DecodedJWT verify = JWTUtils.verifyToken(token);
        String id = verify.getClaim("id").toString();
        String username = verify.getClaim("username").toString();
        Map<String,Object> map = new HashMap<>();

        //处理业务逻辑
        map.put("state", true);
        map.put("msg", "请求成功。业务处理,");
        map.put("id", id);
        map.put("username", username);
        return map;
    }
}
