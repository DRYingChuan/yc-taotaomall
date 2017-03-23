package com.yc.taotao.search.dao;

import com.yc.taotao.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;

/**
 * Created by YcDr on 2017/3/14.
 */
public interface SearchDao {
    public SearchResult search(SolrQuery query) throws Exception;
}
