package com.yc.taotao.service.impl;

import com.yc.common.pojo.EasyuiUITreeNode;
import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.mapper.TbContentCategoryMapper;
import com.yc.taotao.pojo.*;
import com.yc.taotao.service.ItemContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by YcDr on 2017/2/26.
 */
@Service
public class ItemContentCategoryServiceImpl implements ItemContentCategoryService {
    @Autowired
    private TbContentCategoryMapper categoryMapper;
    @Override
    public List<EasyuiUITreeNode> getItemContentService(long parenId) {
        TbContentCategoryExample categoryExample=new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andParentIdEqualTo(parenId);
        List<TbContentCategory> resuList = categoryMapper.selectByExample(categoryExample);
        List<EasyuiUITreeNode> nodeList=new ArrayList<>();
        for(TbContentCategory contentCategory:resuList){
            EasyuiUITreeNode node=new EasyuiUITreeNode();
            node.setId(contentCategory.getId());
            node.setText(contentCategory.getName());
            node.setState(contentCategory.getIsParent()?"closed":"open");
            nodeList.add(node);
        }
        return nodeList;
    }

    @Override
    public TaotaoResult createContentCategory(long Id, String name) {
        TbContentCategory contentCategory=new TbContentCategory();
        Date date=new Date();
        contentCategory.setCreated(date);
        contentCategory.setUpdated(date);
        contentCategory.setName(name);
        contentCategory.setParentId(Id);
        contentCategory.setStatus(1);
        contentCategory.setSortOrder(1);
        contentCategory.setIsParent(false);
        categoryMapper.insert(contentCategory);
        Long id=contentCategory.getId();
        TbContentCategory category = categoryMapper.selectByPrimaryKey(Id);
        if (!category.getIsParent()){
            category.setIsParent(true);
            categoryMapper.updateByPrimaryKey(category);
        }
        return TaotaoResult.ok(id);
    }

    @Override
    public TaotaoResult updateContentCategory(long Id, String name) {
        TbContentCategory category = categoryMapper.selectByPrimaryKey(Id);
        category.setName(name);
        category.setUpdated(new Date());
        categoryMapper.updateByPrimaryKey(category);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteContentCategory(long Id) {
        TbContentCategory category = categoryMapper.selectByPrimaryKey(Id);
        categoryMapper.deleteByPrimaryKey(category.getId());
        if (!category.getIsParent()){
            return TaotaoResult.ok();
        }
        TbContentCategoryExample categoryExample=new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andParentIdEqualTo(Id);
        List<TbContentCategory> reslut = categoryMapper.selectByExample(categoryExample);
        for (TbContentCategory contentCategory:reslut){
            deleteContentCategory(contentCategory.getId());
        }
        return TaotaoResult.ok();
    }



}
