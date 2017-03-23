package com.yc.taotao.sso.service;

import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.pojo.TbUser;

/**
 * Created by YcDr on 2017/3/21.
 */
public interface RegisterService {
    TaotaoResult checkData(String param,int type);
    TaotaoResult register(TbUser user);

}
