package com.yc.taotao.service;

import com.yc.common.pojo.EasyuiUITreeNode;

import java.util.List;

/**
 * Created by YcDr on 2017/2/19.
 */
public interface ItemCatService {
    List<EasyuiUITreeNode> getItemCatList(long parentId);
}
