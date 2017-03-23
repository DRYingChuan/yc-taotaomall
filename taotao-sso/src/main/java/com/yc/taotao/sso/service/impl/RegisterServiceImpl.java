package com.yc.taotao.sso.service.impl;

import com.yc.common.utils.TaotaoResult;
import com.yc.taotao.mapper.TbUserMapper;
import com.yc.taotao.pojo.TbUser;
import com.yc.taotao.pojo.TbUserExample;
import com.yc.taotao.sso.service.RegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by YcDr on 2017/3/21.
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private TbUserMapper userMapper;
    @Override
    public TaotaoResult checkData(String param, int type) {
        TbUserExample userExample=new TbUserExample();
        TbUserExample.Criteria criteria = userExample.createCriteria();
        //type为类型，可选参数1、2、3分别代表username、phone、email
        if (1==type){
            criteria.andUsernameEqualTo(param);
        }else if (2==type){
            criteria.andPhoneEqualTo(param);
        }else if (3==type){
            criteria.andEmailEqualTo(param);
        }
        List<TbUser> list = userMapper.selectByExample(userExample);
        //判断查询结果是否为空
        if (list==null||list.isEmpty()){
           return TaotaoResult.ok(true);
        }
        return TaotaoResult.ok(false);
    }

    @Override
    public TaotaoResult register(TbUser user) {
        //校验数据
        if (StringUtils.isBlank(user.getUsername())
                || StringUtils.isBlank(user.getPassword())){
            return TaotaoResult.build(400,"用户名或密码不能为空");
        }
        //校验数据是否重复
        TaotaoResult taotaoResult=checkData(user.getUsername(),1);
        if (!(boolean)taotaoResult.getData()){
            return TaotaoResult.build(400,"用户名重复");
        }
        //校验手机号
        if (user.getPhone()!=null){
            taotaoResult=checkData(user.getPhone(),2);
            if (!(boolean)taotaoResult.getData()){
                return TaotaoResult.build(400,"手机号重复");
            }
        }
        //校验邮箱
        if (user.getEmail()!=null){
            taotaoResult=checkData(user.getEmail(),3);
            if (!(boolean)taotaoResult.getData()){
                return TaotaoResult.build(400,"邮箱重复");
            }
        }
        //插入数据
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //密码进行MD5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userMapper.insert(user);
        return TaotaoResult.ok();
    }
}
