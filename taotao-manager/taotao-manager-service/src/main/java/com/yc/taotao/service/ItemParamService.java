package com.yc.taotao.service;

import com.yc.common.pojo.EasyuiDataGridResult;
import com.yc.common.pojo.PictureResult;
import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.pojo.TbItemParam;

/**
 * Created by yc on 17-2-23.
 */
public interface ItemParamService {
    EasyuiDataGridResult getParamList(int page, int rows);
    TaotaoResult deleteParam(long id);
    TaotaoResult createItem(TbItemParam tbItemParam);
    TaotaoResult updateParam(TbItemParam tbItemParam);
    TaotaoResult getItemParamByCid(long cid);
}
