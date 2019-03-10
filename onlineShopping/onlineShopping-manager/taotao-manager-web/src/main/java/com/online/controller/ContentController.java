package com.online.controller;

import com.online.common.pojo.EasyUIDataGridResult;
import com.online.common.pojo.TaotaoResult;
import com.online.common.utils.HttpClientUtil;
import com.online.pojo.TbContent;
import com.online.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author iu
 */
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${REST_CONTENT_SYNC_URL}")
    private String REST_CONTENT_SYNC_URL;

    @RequestMapping("/content/query/list")
    @ResponseBody
    public EasyUIDataGridResult getContentList(Long categoryId,Integer page,Integer rows){
        EasyUIDataGridResult contentList = contentService.getContentList(categoryId, page, rows);
        return contentList;
    }
    //插入content数据
    @RequestMapping("/content/save")
    @ResponseBody
    public TaotaoResult insertContent(TbContent tbContent){

        TaotaoResult taotaoResult = contentService.insertContent(tbContent);
        //同步缓存,调用taotao-rest发布的服务
        HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + tbContent.getCategoryId());

        return taotaoResult;
    }


}
