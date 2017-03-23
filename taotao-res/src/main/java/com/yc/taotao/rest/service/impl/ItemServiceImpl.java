package com.yc.taotao.rest.service.impl;


import com.yc.common.utils.JsonUtils;
import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.mapper.TbItemDescMapper;
import com.yc.taotao.mapper.TbItemMapper;
import com.yc.taotao.pojo.TbContent;
import com.yc.taotao.pojo.TbItem;
import com.yc.taotao.pojo.TbItemDesc;
import com.yc.taotao.pojo.TbItemExample;
import com.yc.taotao.rest.component.JedisClient;
import com.yc.taotao.rest.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by YcDr on 2017/3/18.
 *
 */
@Service
public class ItemServiceImpl implements ItemService{
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private JedisClient jedisClient;
    @Autowired
    private TbItemDescMapper itemDescMapper;

    //缓存key
    //商品信息key
    @Value("${REDIS_ITEM_KEY}")
    private  String REDIS_ITEM_KEY;
    //商品基本信息key
    @Value("${ITEM_KEY}")
    private String ITEM_KEY;
    //商品过期时间
    @Value("${ITEM_EXPIRE_SECOND}")
    private Integer ITEM_EXPIRE_SECOND;
    @Override
    public TbItem getItemById(Long itemid) {
        //添加查询缓存
        try {
            String json=jedisClient.get(REDIS_ITEM_KEY+":"+ITEM_KEY+itemid);
            //如果查询缓存不为空，则返回
            if (StringUtils.isNoneBlank(json)) {
                TbItem item=JsonUtils.jsonToPojo(json, TbItem.class);
                return item;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //根据商品id查询商品基本信息
        TbItem item = itemMapper.selectByPrimaryKey(itemid);
        //向redis中添加缓存
        try{
            jedisClient.set(REDIS_ITEM_KEY+":"+ITEM_KEY+itemid, JsonUtils.objectToJson(item));
            //设置过期时间
            jedisClient.expirc(REDIS_ITEM_KEY+":"+ITEM_KEY+itemid,ITEM_EXPIRE_SECOND);
        }catch (Exception e){
            e.printStackTrace();
        }
        return item;
    }
}
