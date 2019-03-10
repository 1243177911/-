package com.online.controller;

import com.online.common.pojo.EasyUIDataGridResult;
import com.online.common.pojo.TaotaoResult;
import com.online.pojo.TbItem;
import com.online.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author iu
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId){
        TbItem item = itemService.getItemByid(itemId);
        return item;
    }


    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page,Integer rows){
        EasyUIDataGridResult easyUIDataGridResult = itemService.getItemList(page,rows);
        return easyUIDataGridResult;
    }


    @RequestMapping(value = "/item/save", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult createItem(TbItem tbItem,String desc,String itemParams){
        TaotaoResult item = itemService.createItem(tbItem,desc,itemParams);
        return item;
    }

    @RequestMapping("/item/param/{itemId}")
    public String showItemParam(@PathVariable long itemId,Model model){
        String itemParamHtml = itemService.getItemParamHtml(itemId);
        model.addAttribute("myhtml",itemParamHtml);
        return "item-param";
    }
}
