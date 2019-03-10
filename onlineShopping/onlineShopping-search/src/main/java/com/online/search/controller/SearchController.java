package com.online.search.controller;

import com.alibaba.druid.util.StringUtils;
import com.online.common.pojo.TaotaoResult;
import com.online.search.pojo.SearchResult;
import com.online.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    @RequestMapping("/search")
    @ResponseBody
    public TaotaoResult search(@RequestParam("keyword") String queryString,
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "60") int rows) throws Exception {
        if(!StringUtils.isEmpty(queryString)) {
            //解决get乱码问题
            queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
            SearchResult search = searchService.search(queryString, page, rows);
            return TaotaoResult.ok(search);
        }else {
            return TaotaoResult.build(400,"查询消息不能为空");
        }
    }
}
