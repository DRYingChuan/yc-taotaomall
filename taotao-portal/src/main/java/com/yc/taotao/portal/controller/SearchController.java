package com.yc.taotao.portal.controller;

import com.yc.common.pojo.SearchResult;
import com.yc.taotao.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @RequestMapping("/search")
    public String search(@RequestParam("q")String keyword,
                         @RequestParam(defaultValue = "1")Integer page,
                         @RequestParam(defaultValue = "30")Integer rows, Model model){
        try {
            keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
        }catch (Exception e){
            keyword = "";
            e.printStackTrace();
        }
        SearchResult searchResult = searchService.search(keyword, page, rows);
        //传递给页面
        model.addAttribute("query", keyword);
        model.addAttribute("totalPages", searchResult.getPageCount());
        model.addAttribute("itemList", searchResult.getItemList());
        model.addAttribute("page", searchResult.getCurPage());
        return "search";
    }

}
