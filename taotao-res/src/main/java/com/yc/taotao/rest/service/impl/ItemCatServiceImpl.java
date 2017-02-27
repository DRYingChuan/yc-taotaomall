package com.yc.taotao.rest.service.impl;

import com.yc.taotao.mapper.TbItemCatMapper;
import com.yc.taotao.pojo.CatNote;
import com.yc.taotao.pojo.ItemCatResult;
import com.yc.taotao.pojo.TbItemCat;
import com.yc.taotao.pojo.TbItemCatExample;
import com.yc.taotao.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public ItemCatResult getItemCatList() {
        List catList=getItemCatList(0l);
        ItemCatResult result=new ItemCatResult();
        result.setData(catList);
        return result;
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
