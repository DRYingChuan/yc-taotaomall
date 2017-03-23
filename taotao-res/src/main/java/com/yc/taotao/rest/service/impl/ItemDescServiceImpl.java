package com.yc.taotao.rest.service.impl;

import com.alibaba.druid.support.json.JSONUtils;

import com.yc.common.utils.JsonUtils;
import com.yc.taotao.mapper.TbItemDescMapper;
import com.yc.taotao.pojo.TbItemDesc;
import com.yc.taotao.rest.component.JedisClient;
import com.yc.taotao.rest.service.ItemDescService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class ItemDescServiceImpl implements ItemDescService {
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private JedisClient jedisClient;
    //商品信息key
    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;
    //商品描述key
    @Value("${ITEM_DEC_KEY}")
    private String ITEM_DEC_KEY;
    @Value("${ITEM_EXPIRE_SECOND}")
    private Integer ITEM_EXPIRE_SECOND;
    @Override
    public TbItemDesc getItemDescById(Long itemid) {
        //查询缓存
        try {
            String json=jedisClient.get(REDIS_ITEM_KEY+":"+ITEM_DEC_KEY+":"+itemid);
            if (StringUtils.isNotBlank(json)){
                TbItemDesc tbItemDesc= JsonUtils.jsonToPojo(json,TbItemDesc.class);
                return tbItemDesc;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //商品描述
       TbItemDesc itemDesc= itemDescMapper.selectByPrimaryKey(itemid);
        //向redis添加缓存
        try {
            jedisClient.set(REDIS_ITEM_KEY+":"+ITEM_DEC_KEY+":"+itemid, JSONUtils.toJSONString(itemDesc));
            //设置过去时间
            jedisClient.expirc(REDIS_ITEM_KEY+":"+ITEM_DEC_KEY+":"+itemid,ITEM_EXPIRE_SECOND);
        }catch (Exception e){
            e.printStackTrace();
        }
        return itemDesc;
    }
}
