package com.online.controller;

import com.online.common.pojo.EasyUIDataGridResult;
import com.online.common.pojo.TaotaoResult;
import com.online.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {

    @Autowired
    private ItemParamService itemParamService;

    @RequestMapping("/list")
    @ResponseBody
    public EasyUIDataGridResult getItemParam(Integer page,Integer rows){
        EasyUIDataGridResult itemParam = itemParamService.getItemParam(page, rows);
        return itemParam;
    }

    @RequestMapping("/query/itemcatid/{cid}")
    @ResponseBody
    public TaotaoResult getParambyCid(@PathVariable long cid){
        return itemParamService.getItemParamByCid(cid);
    }
    @RequestMapping("/save/{cid}")
    @ResponseBody
    public TaotaoResult insertParam(@PathVariable long cid,String paramData){
        TaotaoResult taotaoResult = itemParamService.insertItemParam(cid, paramData);
        return taotaoResult;
    }
}
