package com.yc.taotao.portal.service.impl;

import com.yc.common.utils.HttpClientUtil;
import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.pojo.TbItemDesc;
import com.yc.taotao.portal.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by YcDr on 2017/3/20.
 */
@Service
public class ItemDescServiceImpl implements ItemDescService {
    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    //商品描述信息url
    @Value("${REST_ITEM_DESC_URL}")
    private String REST_ITEM_DESC_URL;
    @Override
    public String getItemDescById(Long itemId) {
        String json = HttpClientUtil.doGet( REST_BASE_URL+ REST_ITEM_DESC_URL + itemId);
        TaotaoResult taotapResult = TaotaoResult.formatToPojo(json, TbItemDesc.class);
        TbItemDesc itemDesc= (TbItemDesc) taotapResult.getData();
        String desc = itemDesc.getItemDesc();
        return desc;
    }
}
