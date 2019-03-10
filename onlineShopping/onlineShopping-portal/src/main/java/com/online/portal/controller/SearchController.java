package com.online.portal.controller;

import com.online.portal.pojo.SearchResult;
import com.online.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/search")
    public String search(@RequestParam("q") String keyword,
                         @RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "60")Integer rows, Model model) throws UnsupportedEncodingException {
        keyword = new String(keyword.getBytes("iso8859-1"),"utf-8");
            SearchResult search = searchService.search(keyword, page, rows);
            model.addAttribute("query",keyword);
            model.addAttribute("totalPages",search.getPageCount());
            model.addAttribute("itemList",search.getItemList());
            model.addAttribute("page",search.getCurPage());
            return "search";
    }
}
