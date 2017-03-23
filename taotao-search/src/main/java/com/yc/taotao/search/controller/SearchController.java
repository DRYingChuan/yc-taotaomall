package com.yc.taotao.search.controller;

import com.yc.common.utils.ExceptionUtil;
import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.search.pojo.SearchResult;
import com.yc.taotao.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by YcDr on 2017/3/14.
 */
@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;
    @RequestMapping("/q")
    @ResponseBody
    public TaotaoResult search(@RequestParam(defaultValue = "")String keyword,
    @RequestParam(defaultValue = "1")Integer page,@RequestParam(defaultValue = "30")Integer rows){
        try {
            keyword=new String(keyword.getBytes("iso8859-1"), "utf-8");
            SearchResult searchResult = searchService.search(keyword, page, rows);
            return TaotaoResult.ok(searchResult);
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
