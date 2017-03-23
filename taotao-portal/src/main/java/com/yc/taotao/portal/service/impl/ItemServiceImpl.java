package com.yc.taotao.portal.service.impl;

import com.yc.common.utils.HttpClientUtil;
import com.yc.common.utils.JsonUtils;
import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.pojo.TbItem;
import com.yc.taotao.portal.pojo.PortalItem;
import com.yc.taotao.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by YcDr on 2017/3/18.
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_ITEM_BASE_URL}")
    private String REST_ITEM_BASE_URL;
    @Override
    public TbItem getItem(Long itemid) {
        String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_BASE_URL + itemid);
        TaotaoResult taotaoResult= TaotaoResult.formatToPojo(json, PortalItem.class);
        TbItem item = (TbItem) taotaoResult.getData();
        return item;
    }
}
