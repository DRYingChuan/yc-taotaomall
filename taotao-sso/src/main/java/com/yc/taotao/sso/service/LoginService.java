package com.yc.taotao.sso.service;

import com.yc.common.utils.TaotaoResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by YcDr on 2017/3/22.
 */
public interface LoginService {
    TaotaoResult Login(String username, String password, HttpServletRequest request, HttpServletResponse response);
}
