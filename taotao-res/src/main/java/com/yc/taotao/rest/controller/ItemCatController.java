package com.yc.taotao.rest.controller;

import com.yc.common.utils.ExceptionUtil;
import com.yc.common.utils.JsonUtils;
import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.pojo.ItemCatResult;
import com.yc.taotao.rest.service.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by YcDr on 2017/2/26.
 */
@RequestMapping("/item/cat")
@Controller
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;
    /*@RequestMapping(value = "/list",produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
    @ResponseBody
    public String getItemCatList(String callback){
        ItemCatResult catResult=itemCatService.getItemCatList();
        if (StringUtils.isBlank(callback)){
            String json= JsonUtils.objectToJson(catResult);
            return json;
        }
        //如果字符串不为空，需要支持jsonp调用
        String json=JsonUtils.objectToJson(catResult);
        return callback+"("+json+");";
    }*/
    /*要求springmvc必须是4.1以上版本。
    MappingJacksonValue
    */
    @RequestMapping(value = "/list",produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
    @ResponseBody
    public Object getItemCatList(String callback){
        ItemCatResult catResult=itemCatService.getItemCatList();
        if (StringUtils.isBlank(callback)){
            String json= JsonUtils.objectToJson(catResult);
            return json;
        }
        //如果字符串不为空，需要支持jsonp调用
        MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(catResult);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }

    @RequestMapping("/sync/itemcat/cat")
    @ResponseBody
    public TaotaoResult sysncContent() {
        try {
            TaotaoResult result = itemCatService.syncContent("cat");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
