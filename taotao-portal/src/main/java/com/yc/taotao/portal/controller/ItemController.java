package com.yc.taotao.portal.controller;

import com.yc.taotao.pojo.TbItem;
import com.yc.taotao.pojo.TbItemDesc;
import com.yc.taotao.portal.service.ItemDescService;
import com.yc.taotao.portal.service.ItemParamService;
import com.yc.taotao.portal.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by YcDr on 2017/3/18.
 */

@Controller
public class ItemController {
    //商品基本信息服务
    @Autowired
    private ItemService itemService;
    //商品描述信息服务
    @Autowired
    private ItemDescService itemDescService;
    //商品规格参数服务
    @Autowired
    private ItemParamService itemParamService;
    @RequestMapping("/item/{itemId}")
    public String showItemInfo(@PathVariable Long itemId,Model model){
        TbItem item = itemService.getItem(itemId);
        model.addAttribute("item",item);
        return "item";
    }
    @RequestMapping(value = "/item/desc/{itemId}",produces = MediaType.TEXT_HTML_VALUE+";charset=utf-8")
    @ResponseBody
    public String getItemDesc(@PathVariable Long itemId){
        String item = itemDescService.getItemDescById(itemId);
        return item;
    }
    @RequestMapping(value = "/item/param/{itemId}",produces = MediaType.TEXT_HTML_VALUE+";charset=utf-8")
    @ResponseBody
    public String getParamItem(@PathVariable Long itemId){
        String item = itemParamService.getParamItemById(itemId);
        return item;
    }

}
