package com.yc.taotao.service;

import com.yc.common.pojo.EasyuiUITreeNode;
import com.yc.common.pojo.PictureResult;
import com.yc.common.utils.TaotaoResult;

import java.util.List;

/**
 * Created by YcDr on 2017/2/26.
 */
public interface ItemContentCategoryService {
    List<EasyuiUITreeNode> getItemContentService(long parenId);
    TaotaoResult createContentCategory(long Id, String name);
    TaotaoResult updateContentCategory(long Id, String name);
    TaotaoResult deleteContentCategory(long Id);
}
