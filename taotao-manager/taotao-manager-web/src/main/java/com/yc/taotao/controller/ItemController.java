package com.yc.taotao.controller;


import com.yc.common.pojo.EasyuiDataGridResult;
import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.pojo.TbItem;
import com.yc.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by YcDr on 2017/2/19.
 */
@RequestMapping("/item")
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/{itemId}")
    @ResponseBody
    private TbItem getItemById(@PathVariable Long itemId) {
        TbItem item = itemService.getItemById(itemId);
        return item;
    }
    @RequestMapping("/list")
    @ResponseBody
    public EasyuiDataGridResult getItemList(Integer page,Integer rows){
        EasyuiDataGridResult result=itemService.getItemList(page,rows);
        return result;
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult createItem(TbItem item,String desc) {
        TaotaoResult result=itemService.createItem(item,desc);
        return result;
    }
}
