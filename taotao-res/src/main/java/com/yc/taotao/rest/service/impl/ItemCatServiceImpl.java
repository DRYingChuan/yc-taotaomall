package com.yc.taotao.rest.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.yc.common.utils.JsonUtils;
import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.mapper.TbItemCatMapper;
import com.yc.taotao.pojo.*;
import com.yc.taotao.rest.component.JedisClient;
import com.yc.taotao.rest.service.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YcDr on 2017/2/26.
 */
@Service
public class ItemCatServiceImpl implements ItemCatService{
     @Autowired
     private TbItemCatMapper itemCatMapper;
     @Autowired
     private JedisClient jedisClient;
     @Value("REDIS_ITEMCAT_KEY")
     private String REDIS_ITEMCAT_KEY;
    @Override
    public ItemCatResult getItemCatList() {
        ItemCatResult result=new ItemCatResult();
        try {
            String json=jedisClient.hget(REDIS_ITEMCAT_KEY,"cat");
            if (!StringUtils.isBlank(json)){
                List list = JsonUtils.jsonToList(json, Object.class);
                result.setData(list);
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        List catList=getItemCatList(0L);
        result.setData(catList);
        try {
            jedisClient.hset(REDIS_ITEMCAT_KEY,"cat", JsonUtils.objectToJson(catList));
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public TaotaoResult syncContent(String cat) {
        jedisClient.hdel(REDIS_ITEMCAT_KEY,cat);
        return TaotaoResult.ok();
    }

    private List getItemCatList(long parentId){

        //根据parentid
        TbItemCatExample catExample=new TbItemCatExample();
        TbItemCatExample.Criteria criteria = catExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> list = itemCatMapper.selectByExample(catExample);
        List resultList=new ArrayList();
        int indext=0;
        for (TbItemCat tbItemCat:list){
            if (indext>=14){
                break;
            }
                CatNote catNote=new CatNote();
                if (tbItemCat.getIsParent()){
                    catNote.setUrl("/products/"+tbItemCat.getId()+".html");
                    if (tbItemCat.getParentId()==0){
                        catNote.setName("<a href='/products/1.html'>"+tbItemCat.getName()+"</a>");
                        indext++;
                    }else {
                        catNote.setName(tbItemCat.getName());
                    }
                    catNote.setItems(getItemCatList(tbItemCat.getId()));
                    resultList.add(catNote);
                }else {
                     String node= "/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName();
                     resultList.add(node);
                }
        }
        return resultList;
    }
}
