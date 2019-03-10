package com.online.portal.service.impl;

import com.online.common.pojo.TaotaoResult;
import com.online.common.utils.HttpClientUtil;
import com.online.portal.pojo.SearchResult;
import com.online.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    @Value("${SEARCH_BASE_URL}")
    private String SEARCH_BASE_URL;

    @Override
    public SearchResult search(String keyword, int page, int rows) {
        //调用搜索服务器查询商品列表
        Map<String,String> param = new HashMap<>();
        param.put("keyword",keyword);
        param.put("page",page+"");
        param.put("rows",rows+"");
        //调用服务
        String json = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
        //json转java对象
        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, SearchResult.class);
        SearchResult searchResult = (SearchResult) taotaoResult.getData();
        return searchResult;
    }
}
