package com.yc.taotao.rest.service;

import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.pojo.ItemCatResult;

/**
 * Created by YcDr on 2017/2/26.
 */
public interface ItemCatService {
    ItemCatResult getItemCatList();
    TaotaoResult syncContent(String cat);
}
