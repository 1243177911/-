package com.online.rest.service;


import com.online.common.pojo.TaotaoResult;
import com.online.rest.pojo.ItemCatResult;

public interface ItemCatService {

    //portal中左上导航栏
    ItemCatResult getItemCatList();
    TaotaoResult syncItemCat();
}
