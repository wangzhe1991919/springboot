package com.wz.springboot.controller;

import com.wz.springboot.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @RequestMapping("login")
    @ResponseBody
    public String login(HttpServletRequest request,String username,String password) {

        System.out.println("username----> has login in");
        if (StringUtils.isEmpty(username)) {
            return "fail";
        }
        User user = new User();
        user.setUsername("admin");
        HttpSession session = request.getSession();
        session.setAttribute("user",user);
        return "loginsuccess";
    }
}

