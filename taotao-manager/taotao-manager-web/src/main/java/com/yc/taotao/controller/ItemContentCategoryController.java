package com.yc.taotao.controller;

import com.yc.common.pojo.EasyuiUITreeNode;
import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.service.ItemContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by YcDr on 2017/2/26.
 */
@Controller
@RequestMapping("/content/category")
public class ItemContentCategoryController {
    @Autowired
    private ItemContentCategoryService contentService;

    ///list
    @RequestMapping(value = "/list")
    @ResponseBody
    public List<EasyuiUITreeNode> getItemContentList(@RequestParam(value = "id",defaultValue = "0") long parenId) {
        List<EasyuiUITreeNode> nodeList = contentService.getItemContentService(parenId);
        return nodeList;
    }
    @RequestMapping(value = "/create")
    @ResponseBody
    public TaotaoResult createItemContent(long parentId,String name) {
        TaotaoResult result = contentService.createContentCategory(parentId, name);
        return result;
    }
    @RequestMapping(value = "/update")
    @ResponseBody
    public TaotaoResult updateItemContent(long id,String name) {
        TaotaoResult result = contentService.updateContentCategory(id,name);
        return result;
    }
    @RequestMapping(value = "/delete")
    @ResponseBody
    public TaotaoResult deleteItemContent(long id) {
        TaotaoResult result = contentService.deleteContentCategory(id);
        return result;
    }


}
