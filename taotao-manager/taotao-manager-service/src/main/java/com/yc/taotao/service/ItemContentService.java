package com.yc.taotao.service;

import com.yc.common.pojo.EasyuiDataGridResult;
import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.pojo.TbContent;

/**
 * Created by YcDr on 2017/2/26.
 */
public interface ItemContentService {
    EasyuiDataGridResult getItemContentList(long categoryid,int page,int rows);
    TaotaoResult createContent(TbContent content);
    TaotaoResult updateContent(TbContent content);
    TaotaoResult deleteContent(long[] id);
}
