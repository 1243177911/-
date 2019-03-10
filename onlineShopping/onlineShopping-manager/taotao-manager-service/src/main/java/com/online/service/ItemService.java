package com.online.service;

import com.online.common.pojo.EasyUIDataGridResult;
import com.online.common.pojo.TaotaoResult;
import com.online.pojo.TbItem;

/**
 * @author iu
 */
public interface ItemService {

    TbItem getItemByid(Long itemId);
    EasyUIDataGridResult getItemList(Integer page,Integer rows);
    TaotaoResult createItem(TbItem tbItem,String desc,String itemParams);
    String getItemParamHtml(long id);
}
