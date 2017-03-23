package com.yc.taotao.search.service;

import com.yc.taotao.search.pojo.SearchResult;

/**
 * Created by YcDr on 2017/3/14.
 */
public interface SearchService {
    public SearchResult search(String queryString, int page, int rows) throws Exception;
}
