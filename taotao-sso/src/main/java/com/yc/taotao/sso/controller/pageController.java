package com.yc.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by YcDr on 2017/3/22.
 */
@Controller
@RequestMapping("/user")
public class pageController {
    //展示注册页面
    @RequestMapping("/page/register")
    public String showPageRegister(){
        return "register";
    }
    //展示登录页面
    @RequestMapping("/page/login")
    public String showLogin(){
        return "login";
    }
}
