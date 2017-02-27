package com.yc.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yc.common.pojo.EasyuiDataGridResult;
import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.mapper.TbContentMapper;
import com.yc.taotao.pojo.TbContent;
import com.yc.taotao.pojo.TbContentExample;
import com.yc.taotao.service.ItemContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by YcDr on 2017/2/26.
 */
@Service
public class ItemContentServiceImpl implements ItemContentService{
    @Autowired
    private TbContentMapper contentMapper;
    @Override
    public EasyuiDataGridResult getItemContentList(long categoryid, int page, int rows) {
        PageHelper.startPage(page,rows);

        List<TbContent> list = contentMapper.selectTbContent(categoryid);

        PageInfo<TbContent> pageInfo=new PageInfo(list);
        //
        EasyuiDataGridResult result=new EasyuiDataGridResult();
        //设置值
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }

    @Override
    public TaotaoResult createContent(TbContent content) {
        contentMapper.insert(content);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult updateContent(TbContent content) {
        contentMapper.updateByPrimaryKeySelective(content);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteContent(long[] ids) {
        for(long id:ids) {
            contentMapper.deleteByPrimaryKey(id);
        }
        return TaotaoResult.ok();
    }
}
