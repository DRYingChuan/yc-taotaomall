package com.yc.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.yc.common.pojo.EasyuiDataGridResult;
import com.yc.common.utils.IDUtils;
import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.mapper.TbItemDescMapper;
import com.yc.taotao.mapper.TbItemMapper;
import com.yc.taotao.pojo.TbItem;
import com.yc.taotao.pojo.TbItemDesc;
import com.yc.taotao.pojo.TbItemDescExample;
import com.yc.taotao.pojo.TbItemExample;
import com.yc.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * Created by YcDr on 2017/2/19.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Override
    public TbItem getItemById(Long itemId) {
        TbItem item = itemMapper.selectByPrimaryKey(itemId);

        return item;
    }

    @Override
    public EasyuiDataGridResult getItemList(int page, int rows) {
        //分页处理
        PageHelper.startPage(page,rows);
        //执行查询
        TbItemExample example=new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);
        PageInfo<TbItem> pageInfo=new PageInfo(list);
        //返回处理结果
        EasyuiDataGridResult result=new EasyuiDataGridResult();
        //设置值
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());

        return result;
    }

    @Override
    public TaotaoResult createItem(TbItem tbItem, String desc) {
        //生成商品ID
        long itemId= IDUtils.genItemId();
        //补全TBITEM属性
        tbItem.setId(itemId);
        tbItem.setStatus((byte)1);
        //创建时间和结束时间
        Date date=new Date();
        tbItem.setCreated(date);
        tbItem.setUpdated(date);
        //插入商品表
        itemMapper.insert(tbItem);
        //商品描述
        TbItemDesc itemDesc=new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        itemDescMapper.insert(itemDesc);
        return TaotaoResult.ok();
    }


}
