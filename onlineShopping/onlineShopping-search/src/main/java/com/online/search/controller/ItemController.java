package com.online.search.controller;

import com.online.common.pojo.TaotaoResult;
import com.online.common.utils.ExceptionUtil;
import com.online.search.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {
    
    @Autowired
    private ItemService itemService;
    
    @RequestMapping("/importall")
    @ResponseBody
    public TaotaoResult importAll(){
        try {
            TaotaoResult taotaoResult = itemService.importItems();
            return taotaoResult;
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }

    }
}
