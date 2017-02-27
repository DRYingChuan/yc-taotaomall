package com.yc.taotao.controller;

import com.yc.common.pojo.EasyuiDataGridResult;
import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.service.ItemParamService;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by YcDr on 2017/2/25.
 */
@RequestMapping("/item/param")
@Controller
public class ItemParamController {
    @Autowired
    private ItemParamService itemParamService;


    @RequestMapping("/query/itemcatid/{cid}")
    @ResponseBody
    public TaotaoResult checkItemParam(@PathVariable long cid){
        TaotaoResult taotaoResult=itemParamService.getItemParamByCid(cid);
        return taotaoResult;
    }

    @RequestMapping(value = "/save/{cid}",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult addItemParam(@PathVariable long cid,String paramData){
        TaotaoResult taotaoResult=itemParamService.createItem(paramData,cid);
        return taotaoResult;
    }
    @RequestMapping("/list")
    @ResponseBody
    public EasyuiDataGridResult getItemParamList(int page,int rows){
        EasyuiDataGridResult result=itemParamService.getParamList(page,rows);
        return result;
    }
}
