package com.yc.taotao.search.dao.impl;

import com.yc.taotao.search.dao.SearchDao;
import com.yc.taotao.search.pojo.SearchItem;
import com.yc.taotao.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by YcDr on 2017/3/14.
 */
@Repository
public class SearchDaoImpl implements SearchDao {
    @Autowired
    private SolrServer solrServer;
    @Override
    public SearchResult search(SolrQuery query) throws Exception {
        //执行查询
        QueryResponse response = solrServer.query(query);
        //获取查询列表
        SolrDocumentList solrDocuments = response.getResults();
        List<SearchItem> itemList=new ArrayList<>();
        for (SolrDocument solrDocument:solrDocuments){
            //创建一个serarchItem对象
            SearchItem item=new SearchItem();
            item.setCategory_name((String) solrDocument.get("item_category_name"));
            item.setId((String) solrDocument.get("id"));
            item.setImage((String) solrDocument.get("item_image"));
            item.setPrice((Long) solrDocument.get("item_price"));
            item.setSell_point((String) solrDocument.get("item_sell_point"));
            //去高亮对象
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            List<String> list=highlighting.get(solrDocument.get("id")).get("item_title");
            String itemTilte="";
            if (list!=null&&list.size()>0){
                //去高亮后的结果
                itemTilte=list.get(0);
            }else {
                itemTilte= (String) solrDocument.get("item_title");
            }
            item.setTitle(itemTilte);
            //添加到列表
            itemList.add(item);
        }
        SearchResult result=new SearchResult();
        result.setItemList(itemList);
        result.setRecordCount(solrDocuments.getNumFound());
        return result;
    }
}
