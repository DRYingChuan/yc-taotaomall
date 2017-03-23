package com.yc.taotao.controller;

import com.yc.common.pojo.EasyuiUITreeNode;
import com.yc.taotao.service.ItemCatService;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by YcDr on 2017/2/19.
 */
@RequestMapping("/item/cat")
@Controller
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;
    @RequestMapping("/list")
    @ResponseBody
    public List<EasyuiUITreeNode> getEasyuiUTreeNodeList(@RequestParam(value = "id",defaultValue = "0") Long parenId){
        List<EasyuiUITreeNode> list = itemCatService.getItemCatList(parenId);
        return list;
    }


}
