package com.yc.taotao.sso.controller;

import com.yc.common.utils.ExceptionUtil;
import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by YcDr on 2017/3/22.
 */
@Controller
@RequestMapping("/user")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @RequestMapping("/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token,String callback){
        try {
            TaotaoResult taotaoResult = tokenService.getUserByToken(token);
            if (StringUtils.isNotBlank(callback)){
                MappingJacksonValue jacksonValue=new MappingJacksonValue(taotaoResult);
                jacksonValue.setJsonpFunction(callback);
                return jacksonValue;
            }
            return taotaoResult;
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/logout/{token}")
    @ResponseBody
    public Object removeUserByToken(@PathVariable String token,String callback){
        try {
            TaotaoResult taotaoResult = tokenService.removeUserByToken(token);
            if (StringUtils.isNotBlank(callback)){
                MappingJacksonValue jacksonValue=new MappingJacksonValue(taotaoResult);
                jacksonValue.setJsonpFunction(callback);
                return jacksonValue;
            }
            return taotaoResult;
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
