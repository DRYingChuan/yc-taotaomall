package com.yc.taotao.portal.service;


import com.yc.common.pojo.SearchResult;

/**
 * Created by YcDr on 2017/3/14.
 */
public interface SearchService {
    public SearchResult search(String keyword, int page, int rows);
}
