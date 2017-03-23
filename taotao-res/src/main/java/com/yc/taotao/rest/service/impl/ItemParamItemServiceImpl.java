package com.yc.taotao.rest.service.impl;

import com.yc.common.utils.JsonUtils;
import com.yc.taotao.mapper.TbItemParamItemMapper;
import com.yc.taotao.pojo.TbItemParamItem;
import com.yc.taotao.pojo.TbItemParamItemExample;
import com.yc.taotao.rest.component.JedisClient;
import com.yc.taotao.rest.service.ItemParamService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by YcDr on 2017/3/18.
 */
@Service
public class ItemParamItemServiceImpl implements ItemParamService {
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;
    @Autowired
    private JedisClient jedisClient;
    //商品信息
    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;
    //规格参数key
    @Value("${ITEM_PARAM_KEY}")
    private String ITEM_PARAM_KEY;
    //过期时间
    @Value("${ITEM_EXPIRE_SECOND}")
    private Integer ITEM_EXPIRE_SECOND;
    @Override
    public TbItemParamItem getTbItemParamItem(Long itemid) {
        try{
            //查询缓存
            String json=jedisClient.get(REDIS_ITEM_KEY+":"+ITEM_PARAM_KEY+":"+itemid);
            System.out.println(StringUtils.isNotBlank(json)+":"+json);
            if (StringUtils.isNotBlank(json)&&!"null".equals(json)){
                TbItemParamItem itemParamItem=JsonUtils.jsonToPojo(json,TbItemParamItem.class);
                return itemParamItem;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        // 根据商品id查询规格参数
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemid);
        List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
        //取规格参数
        if (list != null && list.size() > 0) {
            TbItemParamItem itemParamItem = list.get(0);
            //添加缓存
            try {
                //向redis中添加缓存
                jedisClient.set(REDIS_ITEM_KEY+":"+ITEM_PARAM_KEY+":"+itemid
                        , JsonUtils.objectToJson(itemParamItem));
                //设置key的过期时间
                jedisClient.expirc(REDIS_ITEM_KEY+":"+ITEM_PARAM_KEY+":"+itemid, ITEM_EXPIRE_SECOND);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return itemParamItem;
        }
        return null;
    }
}
