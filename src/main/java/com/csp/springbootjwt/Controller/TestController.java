package com.csp.springbootjwt.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {

    @GetMapping("/test/test")
    public String test1(String username, HttpServletRequest request){
        //认证成功以后，放入session
        request.getSession().setAttribute("username",username);
        System.out.println("username:" + username);
        return "login ok!";

    }

    @RequestMapping("/hello")
    public String sayhello(){
        return "hello,jwt!";
    }

}
