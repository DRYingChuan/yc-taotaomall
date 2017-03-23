package com.yc.taotao.search.service.impl;

import com.yc.taotao.search.dao.SearchDao;
import com.yc.taotao.search.pojo.SearchResult;
import com.yc.taotao.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by YcDr on 2017/3/14.
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SearchDao searchDao;
    @Override
    public SearchResult search(String queryString, int page, int rows) throws Exception {
        //创建查询条件
        SolrQuery query=new SolrQuery();
        //设置查询条件
        query.setQuery(queryString);
        //设置分页
        query.setStart((page-1)*rows);
        query.setRows(rows);
        //设置默认搜索域
        query.set("df","item_title");
        //设置高亮
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<font class=\"skcolor_ljg\">");
        query.setHighlightSimplePost("</font>");
        //执行查询
        SearchResult searchResult=searchDao.search(query);
        //计算总页
        Long recordCount=searchResult.getRecordCount();
        int pageConut= (int) (recordCount/rows);
        if (recordCount%rows>0){
            pageConut++;
        }
        searchResult.setPageCount(pageConut);
        searchResult.setCurPage(page);
        return searchResult;
    }
}
