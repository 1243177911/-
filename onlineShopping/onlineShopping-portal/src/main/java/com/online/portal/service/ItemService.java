package com.online.portal.service;

import com.online.pojo.TbItem;

public interface ItemService {

    //调用查询商品基本信息服务
    TbItem getItemById(Long itemId);
    //调用查询商品描述服务
    String getItemDescById(Long itemId);
    //调用查询商品规格参数服务
    String getItemParamById(Long itemId);
}
