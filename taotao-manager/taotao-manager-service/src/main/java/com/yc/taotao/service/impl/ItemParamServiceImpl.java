package com.yc.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yc.common.pojo.EasyuiDataGridResult;
import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.mapper.TbItemParamMapper;
import com.yc.taotao.pojo.TbItemParam;
import com.yc.taotao.pojo.TbItemParamExample;
import com.yc.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by yc on 17-2-23.
 */
public class ItemParamServiceImpl implements ItemParamService {
    @Autowired
    private TbItemParamMapper itemParamMapper;

    @Override
    public EasyuiDataGridResult getParamList(int page, int rows) {
        //设置分页
        PageHelper.startPage(page,rows);
        //查询数据
        TbItemParamExample itemParamExample=new TbItemParamExample();
        List<TbItemParam> list = itemParamMapper.selectByExample(itemParamExample);
        //参数设置
        PageInfo<TbItemParam> pageInfo=new PageInfo<TbItemParam>(list);
        //
        EasyuiDataGridResult easyuiDataGridResult=new EasyuiDataGridResult();
        easyuiDataGridResult.setRows(pageInfo.getList());
        easyuiDataGridResult.setTotal(pageInfo.getTotal());
        return easyuiDataGridResult;
    }

    @Override
    public TaotaoResult deleteParam(long id) {
        itemParamMapper.deleteByPrimaryKey(id);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult createItem(TbItemParam tbItemParam) {
        itemParamMapper.insert(tbItemParam);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult updateParam(TbItemParam tbItemParam) {

        itemParamMapper.updateByPrimaryKey(tbItemParam);
        return TaotaoResult.ok();

    }

    @Override
    public TaotaoResult getItemParamByCid(long cid) {
        return null;
    }
}
