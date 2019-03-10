package com.online.sso.service.impl;

import com.online.common.pojo.TaotaoResult;
import com.online.common.utils.CookieUtils;
import com.online.common.utils.JsonUtils;
import com.online.mapper.TbUserMapper;
import com.online.pojo.TbUser;
import com.online.pojo.TbUserExample;
import com.online.sso.component.JedisClient;
import com.online.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * @author iu
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_SESSION_KEY}")
    private String REDIS_SESSION_KEY;

    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public TaotaoResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {

        TbUserExample exampl = new TbUserExample();
        TbUserExample.Criteria criteria = exampl.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> users = userMapper.selectByExample(exampl);
        if(users == null || users.isEmpty()){
            return TaotaoResult.build(400,"用户名或密码错误");
        }
        //取出用户信息
        TbUser user = users.get(0);
        //校验密码
        if(!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
            return TaotaoResult.build(400,"用户名或密码错误");
        }
        //登录成功
        //生成token
        String token = UUID.randomUUID().toString();
        //把用户信息写入redis
        user.setPassword(null);
        jedisClient.set(REDIS_SESSION_KEY + ":" + token,JsonUtils.objectToJson(user));
        //设置session的过期时间
        jedisClient.expire(REDIS_SESSION_KEY + ":" + token,SESSION_EXPIRE);
        //写cookie(浏览器关闭cookie生效)
        CookieUtils.setCookie(request,response,"TT_TOKEN",token);
        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        String json = jedisClient.get(REDIS_SESSION_KEY + ":" + token);
        //判断是否查询到结果
        if(StringUtils.isEmpty(json)){
            return  TaotaoResult.build(400,"用户session已经过期");
        }
        //把json转为java对象
        TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
        jedisClient.expire(REDIS_SESSION_KEY + ":" + token,SESSION_EXPIRE);
        return TaotaoResult.ok(user);
    }

    @Override
    public TaotaoResult logout(String token,HttpServletResponse response,HttpServletRequest request) {
            jedisClient.del(REDIS_SESSION_KEY + ":" + token);
            CookieUtils.deleteCookie(request,response,"TT_TOKEN");
            return TaotaoResult.ok();
    }
}
