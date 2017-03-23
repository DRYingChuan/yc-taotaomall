package com.yc.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yc.common.pojo.EasyuiDataGridResult;
import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.mapper.TbItemParamMapper;
import com.yc.taotao.pojo.TbItemParam;
import com.yc.taotao.pojo.TbItemParamExample;
import com.yc.taotao.service.ItemParamService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yc on 17-2-23.
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {
    @Autowired
    private TbItemParamMapper itemParamMapper;

    @Override
    public EasyuiDataGridResult getParamList(int page, int rows) {
        //设置分页
        PageHelper.startPage(page,rows);
        //查询数据
        TbItemParamExample itemParamExample=new TbItemParamExample();
        List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(itemParamExample);
        List<TbItemParam> listX=new ArrayList<>();
        //参数设置
        PageInfo<TbItemParam> pageInfo=new PageInfo(list);
        //
        EasyuiDataGridResult result=new EasyuiDataGridResult();
        //设置值
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        System.out.printf(result.toString());
        return result;
    }

    @Override
    public TaotaoResult deleteParam(long id) {
        itemParamMapper.deleteByPrimaryKey(id);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult createItem(String paramData,long id) {
        TbItemParam ItemParam=new TbItemParam();
        Date date=new Date();
        ItemParam.setCreated(date);
        ItemParam.setUpdated(date);
        ItemParam.setItemCatId(id);
        ItemParam.setParamData(paramData);
        try {
            itemParamMapper.insert(ItemParam);
        }catch (Exception e){
            return TaotaoResult.build(500, ExceptionUtils.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult updateParam(TbItemParam tbItemParam) {

        itemParamMapper.updateByPrimaryKey(tbItemParam);
        return TaotaoResult.ok();

    }

    @Override
    public TaotaoResult getItemParamByCid(long cid) {

        TbItemParamExample example=new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(cid);
        List<TbItemParam> list=itemParamMapper.selectByExampleWithBLOBs(example);
        if (null!=list&&!list.isEmpty()){
            return TaotaoResult.ok(list.get(0));
        }
        return TaotaoResult.build(400,"次分类未定义规格模板");

    }
}
