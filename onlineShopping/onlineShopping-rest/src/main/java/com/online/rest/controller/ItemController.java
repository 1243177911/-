package com.online.rest.controller;

import com.online.common.pojo.TaotaoResult;
import com.online.common.utils.ExceptionUtil;
import com.online.pojo.TbItem;
import com.online.pojo.TbItemDesc;
import com.online.pojo.TbItemParamItem;
import com.online.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    //查询商品基本信息
    @RequestMapping("/item/base/{itemId}")
    @ResponseBody
    public TaotaoResult getItemById(@PathVariable Long itemId){
        try {
            TbItem item = itemService.getItemById(itemId);
            return TaotaoResult.ok(item);
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    //查询商品描述
    @RequestMapping("/item/desc/{itemId}")
    @ResponseBody
    public TaotaoResult getItemDescById(@PathVariable Long itemId){
        try {
            TbItemDesc itemdesc = itemService.getItemDescById(itemId);
            return TaotaoResult.ok(itemdesc);
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    //查询商品描述
    @RequestMapping("/item/param/{itemId}")
    @ResponseBody
    public TaotaoResult getItemParamItemById(@PathVariable Long itemId){
        try {
            TbItemParamItem itemParamItem = itemService.getItemParamItemById(itemId);
            return TaotaoResult.ok(itemParamItem);
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
