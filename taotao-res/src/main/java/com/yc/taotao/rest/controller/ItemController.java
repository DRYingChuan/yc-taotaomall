package com.yc.taotao.rest.controller;

import com.yc.common.utils.ExceptionUtil;
import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.pojo.TbItem;
import com.yc.taotao.pojo.TbItemDesc;
import com.yc.taotao.pojo.TbItemParamItem;
import com.yc.taotao.rest.service.ItemDescService;
import com.yc.taotao.rest.service.ItemParamService;
import com.yc.taotao.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@RequestMapping("/item")
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemDescService itemDescService;
    @Autowired
    private ItemParamService itemParamService;
    /**
     * Gets item.
     *
     * @param itemid the itemid
     * @return the item
     */
    @RequestMapping("/base/{itemid}")
    @ResponseBody
    public TaotaoResult getItem(@PathVariable Long itemid){
        try {
            TbItem item = itemService.getItemById(itemid);
            return TaotaoResult.ok(item);
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * Get item desc taotao result.
     *
     * @param itemid the itemid
     * @return the taotao result
     */
    @RequestMapping("/desc/{itemid}")
    @ResponseBody
    public TaotaoResult getItemDesc(@PathVariable Long itemid){
        try {
            TbItemDesc itemdesc = itemDescService.getItemDescById(itemid);
            return TaotaoResult.ok(itemdesc);
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
    }
    @RequestMapping("/param/{itemid}")
    @ResponseBody
    public TaotaoResult getItemParamItem(@PathVariable Long itemid){
        try {
            TbItemParamItem itemParam = itemParamService.getTbItemParamItem(itemid);
            return TaotaoResult.ok(itemParam);
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
    }

}
