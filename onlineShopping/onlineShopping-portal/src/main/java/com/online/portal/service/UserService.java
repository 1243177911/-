package com.online.portal.service;

import com.online.pojo.TbUser;

public interface UserService {
    /**
     * 拦截器通过cookie获得token，调用sso服务通过token查询user对象
     */
    TbUser getUserByToken(String token);
}
