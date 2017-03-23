package com.yc.taotao.sso.service;

import com.yc.common.utils.TaotaoResult;

/**
 * Created by YcDr on 2017/3/22.
 */
public interface TokenService {
    TaotaoResult getUserByToken(String token);
    TaotaoResult removeUserByToken(String token);
}
