package com.yc.taotao.controller;

import com.yc.common.pojo.EasyuiDataGridResult;
import com.yc.common.utils.HttpClientUtil;
import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.pojo.TbContent;
import com.yc.taotao.service.ItemContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by YcDr on 2017/2/26.
 */
@RequestMapping("/content")
@Controller
public class ItemContentController {
    @Autowired
    private ItemContentService contentService;
    @Value("REST_BASE_URL")
    private String REST_BASE_URL;
    @Value("REST_CONTENT_SYNC_URL")
    private String REST_CONTENT_SYNC_URL;
    @RequestMapping("/query/list")
    @ResponseBody
    public EasyuiDataGridResult getItemContentList(long categoryId,int page,int rows){
        EasyuiDataGridResult result = contentService.getItemContentList(categoryId, page, rows);
        return result;
    }
    @RequestMapping("/save")
    @ResponseBody
    public TaotaoResult save(TbContent tbContent){
       // EasyuiDataGridResult result = contentService.getItemContentList(categoryId, page, rows);
        TaotaoResult taotaoResult= contentService.createContent(tbContent);
        HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+tbContent.getCategoryId());
        return taotaoResult;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public TaotaoResult update(TbContent tbContent){
        // EasyuiDataGridResult result = contentService.getItemContentList(categoryId, page, rows);
        TaotaoResult taotaoResult= contentService.updateContent(tbContent);
        HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+tbContent.getCategoryId());
        return taotaoResult;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult save(long[] ids){
        // EasyuiDataGridResult result = contentService.getItemContentList(categoryId, page, rows);
        TaotaoResult taotaoResult= contentService.deleteContent(ids);
        for (long L:ids){
            HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+L);
        }
        return taotaoResult;
    }
}
