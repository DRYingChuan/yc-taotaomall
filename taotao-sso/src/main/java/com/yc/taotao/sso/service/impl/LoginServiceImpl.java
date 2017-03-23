package com.yc.taotao.sso.service.impl;

import com.yc.common.utils.CookieUtils;
import com.yc.common.utils.JsonUtils;
import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.mapper.TbUserMapper;
import com.yc.taotao.pojo.TbUser;
import com.yc.taotao.pojo.TbUserExample;
import com.yc.taotao.sso.component.JedisClient;
import com.yc.taotao.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * Created by YcDr on 2017/3/22.
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${REDIS_SESSION}")
    private String REDIS_SESSION;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public TaotaoResult Login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        //校验用户名和密码是否正确
        TbUserExample userExample=new TbUserExample();
        TbUserExample.Criteria criteria = userExample.createCriteria();
         criteria.andUsernameEqualTo(username);
        List<TbUser> list = userMapper.selectByExample(userExample);
        if (list!=null&&list.isEmpty()){
            return TaotaoResult.build(400,"用户名或密码错误");
        }
        TbUser user=list.get(0);
        //校验密码
        if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
            return TaotaoResult.build(400,"用户名或密码错误");
        }
        //登录成功
        //生成token
        String token= UUID.randomUUID().toString();
        //用户信息写入redis
        //key:REDIS_SESSION:{token}
        //value:user转json
        jedisClient.set(REDIS_SESSION+":"+token, JsonUtils.objectToJson(user));
        //设置session过期时间
        jedisClient.expirc(REDIS_SESSION+":"+token,SESSION_EXPIRE);
        //写入cookie
        CookieUtils.setCookie(request,response,"TT_TOKEN",token);
        return TaotaoResult.ok(token);
    }
}
