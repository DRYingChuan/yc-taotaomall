package com.yc.taotao.sso.service.impl;

import com.yc.common.utils.JsonUtils;
import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.mapper.TbUserMapper;
import com.yc.taotao.pojo.TbUser;
import com.yc.taotao.sso.component.JedisClient;
import com.yc.taotao.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by YcDr on 2017/3/22.
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${REDIS_SESSION}")
    private String REDIS_SESSION;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;
    @Override
    public TaotaoResult getUserByToken(String token) {
        String json = jedisClient.get(REDIS_SESSION + ":" + token);
        if (StringUtils.isBlank(json)){
            return TaotaoResult.build(400,"用户session已经过期");
        }
        TbUser tbUser = JsonUtils.jsonToPojo(json, TbUser.class);
        //更新sesion的过期时间
        jedisClient.expirc(REDIS_SESSION + ":" + token,SESSION_EXPIRE);
        return TaotaoResult.ok(tbUser);
    }

    @Override
    public TaotaoResult removeUserByToken(String token) {
        String json = jedisClient.get(REDIS_SESSION + ":" + token);
        if (!StringUtils.isBlank(json)){
            jedisClient.hdel(REDIS_SESSION + ":" + token);
            return TaotaoResult.ok();
        }
        return TaotaoResult.ok();
    }
}
