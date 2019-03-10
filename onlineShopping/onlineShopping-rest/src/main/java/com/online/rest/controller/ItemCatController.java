package com.online.rest.controller;

import com.alibaba.druid.util.StringUtils;
import com.online.common.pojo.TaotaoResult;
import com.online.common.utils.JsonUtils;
import com.online.rest.pojo.ItemCatResult;
import com.online.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author iu
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public String getItemCatList(String callback) {
        ItemCatResult itemCatList = itemCatService.getItemCatList();
        if (StringUtils.isEmpty(callback)) {
            String json = JsonUtils.objectToJson(itemCatList);
            return json;
        }
        String json = JsonUtils.objectToJson(itemCatList);
        return callback + "(" + json + ");";
    }
    @RequestMapping("/sync/all")
    @ResponseBody
    public TaotaoResult syncItemCat(){
        TaotaoResult taotaoResult = itemCatService.syncItemCat();
        return taotaoResult;
    }


/*    @RequestMapping(value = "/list")
    @ResponseBody
    public Object getItemCatList(String callback) {
        ItemCatResult itemCatList = itemCatService.getItemCatList();
        if (StringUtils.isEmpty(callback)) {
            return itemCatList;
        }
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(itemCatList);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }*/
}
