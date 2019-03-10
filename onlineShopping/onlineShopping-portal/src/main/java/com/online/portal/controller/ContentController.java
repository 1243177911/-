package com.online.portal.controller;

import com.online.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 大广告位
 * @author iu
 */
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/index")
    public String showIndex(Model model){
        String ad1List = contentService.getAd1List();
        model.addAttribute("ad1",ad1List);
        return "index";
    }
}
