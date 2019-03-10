package com.online.sso.service;

import com.online.common.pojo.TaotaoResult;
import com.online.pojo.TbUser;

public interface RegisterService {

    TaotaoResult checkData(String param,int type);

    TaotaoResult register(TbUser user);

}
