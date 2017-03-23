package com.yc.taotao.sso.controller;

import com.yc.common.utils.ExceptionUtil;
import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.pojo.TbUser;
import com.yc.taotao.sso.service.RegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by YcDr on 2017/3/21.
 */
@Controller
@RequestMapping("/user")
public class RegisterController {
    @Autowired
    private RegisterService registerService;
    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public Object checkData(@PathVariable String param,@PathVariable Integer type,String callback){
        try {
            TaotaoResult result=registerService.checkData(param,type);
            if (StringUtils.isNotBlank(callback)){
                //请求为jsonp调用，需要支持
                MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(result);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            }
                return result;
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
    @RequestMapping("/register")
    @ResponseBody
    public TaotaoResult register(TbUser user){
        try {
            TaotaoResult result= registerService.register(user);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }

    }
}
