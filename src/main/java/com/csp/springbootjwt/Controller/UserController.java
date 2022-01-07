package com.csp.springbootjwt.Controller;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.csp.springbootjwt.entity.User;
import com.csp.springbootjwt.service.UserService;
import com.csp.springbootjwt.utils.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
//        log.info("用户名[{}]",user.getUsername());
//        log.info("用户密码[{}]",user.getUserpwd());

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

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/test1")
    public Map<String,Object> test(HttpServletRequest request){

        String token = request.getHeader("token");
        DecodedJWT verify = JWTUtils.verifyToken(token);
        String id = verify.getClaim("id").toString();
        String username = verify.getClaim("username").toString();

        logger.info("id:{}",id);
        logger.info("username:{}",username);

        Map<String,Object> map = new HashMap<>();
        //处理业务逻辑
        map.put("state", true);
        map.put("msg", "请求成功。业务处理,");
        map.put("id", id);
        map.put("username", username);
        return map;
    }
}
