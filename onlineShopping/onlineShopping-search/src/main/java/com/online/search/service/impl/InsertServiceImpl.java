package com.online.search.service.impl;

import com.online.common.pojo.TaotaoResult;
import com.online.search.dao.InsertDao;
import com.online.search.service.InsertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InsertServiceImpl implements InsertService {

    @Autowired
    private InsertDao insertDao;

    @Override
    public TaotaoResult insert(Long id) throws Exception {
        TaotaoResult taotaoResult = insertDao.insert(id);
        return taotaoResult;
    }
}
