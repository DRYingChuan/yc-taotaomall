package com.yc.taotao.rest.service;

import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.pojo.TbItem;

/**
 * Created by YcDr on 2017/3/18.
 */
public interface ItemService {
    TbItem getItemById(Long itemid);
}
