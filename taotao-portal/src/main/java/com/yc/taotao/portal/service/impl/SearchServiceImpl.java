package com.yc.taotao.portal.service.impl;

import com.yc.common.pojo.SearchResult;
import com.yc.common.utils.HttpClientUtil;
import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YcDr on 2017/3/14.
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Value("${SEARCH_BASE_URL}")
    private String SEARCH_BASE_URL;
    @Override
    public SearchResult search(String keyword, int page, int rows) {
        Map<String,String> param=new HashMap<>();
        param.put("keyword",keyword);
        param.put("page",page+"");
        param.put("rows",rows+"");
        String json=HttpClientUtil.doGet(SEARCH_BASE_URL,param);
        TaotaoResult taotaoResult =TaotaoResult.formatToPojo(json,SearchResult.class);
        SearchResult searchResult= (SearchResult) taotaoResult.getData();
        return searchResult;
    }
}
