package com.online.portal.service.impl;

import com.online.common.pojo.TaotaoResult;
import com.online.common.utils.HttpClientUtil;
import com.online.pojo.TbUser;
import com.online.portal.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    
    @Value("${SSO_BASE_URL}")
    public String SSO_BASE_URL;    
    
    @Value("${SSO_TOKEN_URL}")
    public String SSO_TOKEN_URL;

    @Value("${SSO_PAGE_LOGIN}")
    public String SSO_PAGE_LOGIN;

    @Override
    public TbUser getUserByToken(String token) {
        String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_TOKEN_URL + token);
        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbUser.class);
        if(taotaoResult.getData() != null){
            return (TbUser) taotaoResult.getData();
        }
        return null;
    }
}
