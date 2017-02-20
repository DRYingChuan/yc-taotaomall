package com.yc.taotao.service;


import com.yc.common.pojo.EasyuiDataGridResult;
import com.yc.taotao.pojo.TbItem;


/**
 * Created by YcDr on 2017/2/19.
 */
public interface ItemService {
    TbItem getItemById(Long id);
    EasyuiDataGridResult getItemList(int page, int rows);
}
