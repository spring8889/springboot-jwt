package com.csp.springbootjwt.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Controller
public class FileDealer {

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(MultipartFile fileTest, HttpServletRequest request) throws IOException {
        //获取文件的原始名
        String filename = fileTest.getOriginalFilename();
        System.out.println(filename);
        //根据相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/");

        fileTest.transferTo(new File(realPath,filename));

        return "success";
    }

    @RequestMapping("/file")
    public String tofile(){
        return "/fileupload";
    }
}
