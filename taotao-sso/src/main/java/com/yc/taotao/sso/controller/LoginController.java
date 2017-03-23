package com.yc.taotao.sso.controller;

import com.yc.common.utils.ExceptionUtil;
import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by YcDr on 2017/3/22.
 */
@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/login")
    @ResponseBody
    public TaotaoResult Login(HttpServletRequest request, HttpServletResponse response,
                               String username,String password){
        try {
            TaotaoResult reult = loginService.Login(username, password, request, response);
            return  reult;
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
