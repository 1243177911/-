package com.online.rest.service;

import com.online.pojo.TbItem;
import com.online.pojo.TbItemDesc;
import com.online.pojo.TbItemParamItem;

public interface ItemService {
    //商品所有信息
    TbItem getItemById(Long itemId);
    //商品描述
    TbItemDesc getItemDescById(Long itemId);
    //商品规格参数
    TbItemParamItem getItemParamItemById(Long itemId);
}
