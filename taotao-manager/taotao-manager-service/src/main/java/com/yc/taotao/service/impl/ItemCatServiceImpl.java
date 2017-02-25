package com.yc.taotao.service.impl;

import com.yc.common.pojo.EasyuiUITreeNode;
import com.yc.taotao.mapper.TbItemCatMapper;
import com.yc.taotao.pojo.TbItemCat;
import com.yc.taotao.pojo.TbItemCatExample;
import com.yc.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by YcDr on 2017/2/19.
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Override
    public List<EasyuiUITreeNode> getItemCatList(long parentId) {
        TbItemCatExample example=new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
        List<EasyuiUITreeNode> easyuiUITreeNodeList=new ArrayList<>();
        for (TbItemCat tbItemCat:list){
            EasyuiUITreeNode node=new EasyuiUITreeNode();
            node.setId(tbItemCat.getId());
            node.setText(tbItemCat.getName());
            node.setState(tbItemCat.getIsParent()?"closed":"open");
            easyuiUITreeNodeList.add(node);
        }

        return easyuiUITreeNodeList;
    }
}
