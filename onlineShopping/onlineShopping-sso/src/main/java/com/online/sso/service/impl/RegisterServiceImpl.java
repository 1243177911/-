package com.online.sso.service.impl;

import com.online.common.pojo.TaotaoResult;
import com.online.mapper.TbUserMapper;
import com.online.pojo.TbUser;
import com.online.pojo.TbUserExample;
import com.online.sso.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private TbUserMapper userMapper;

    @Override
    public TaotaoResult checkData(String param, int type) {
        //根据数据类型检查数据
        TbUserExample exampl = new TbUserExample();
        TbUserExample.Criteria criteria = exampl.createCriteria();
        //1，2，3代表username，phone，email
        if(1 == type){
            criteria.andUsernameEqualTo(param);
        }else if(2 == type){
            criteria.andPhoneEqualTo(param);
        }else if (3 == type){
            criteria.andEmailEqualTo(param);
        }
        //查询
        List<TbUser> users = userMapper.selectByExample(exampl);
        if(users == null || users.isEmpty()){
            return TaotaoResult.ok(true);
        }
        return TaotaoResult.ok(false);
    }

    @Override
    public TaotaoResult register(TbUser user) {
        //校验数据(不能为空)
        if(user.getUsername().isEmpty() || user.getPassword().isEmpty()){
            return TaotaoResult.build(400,"用户名或密码不能为空");
        }
        //校验数据(重复)
        TaotaoResult taotaoResultUserName = checkData(user.getUsername(), 1);
            if(!(Boolean) taotaoResultUserName.getData()){
            return TaotaoResult.build(400,"用户名重复");
        }
        //校验数据(手机号)
        if(user.getPhone() != null){
            TaotaoResult taotaoResultPhone = checkData(user.getPhone(), 2);
            if(!(Boolean) taotaoResultPhone.getData()){
                return TaotaoResult.build(400,"手机号重复");
            }
        }
        //校验数据(邮箱)
        if(user.getEmail() != null){
            TaotaoResult taotaoResultPhone = checkData(user.getEmail(), 3);
            if(!(Boolean) taotaoResultPhone.getData()){
                return TaotaoResult.build(400,"邮箱重复");
            }
        }
        //补全数据 并 插入数据
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //密码进行MD5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userMapper.insert(user);
        return TaotaoResult.ok();
    }
}
