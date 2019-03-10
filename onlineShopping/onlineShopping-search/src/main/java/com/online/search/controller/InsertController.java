package com.online.search.controller;

import com.online.common.pojo.TaotaoResult;
import com.online.search.service.InsertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InsertController {

    @Autowired
    private InsertService insertService;

    @RequestMapping("/insert/{id}")
    @ResponseBody
    public TaotaoResult insert(@PathVariable Long id) throws Exception {
        TaotaoResult taotaoResult = insertService.insert(id);
        return taotaoResult;
    }
}
