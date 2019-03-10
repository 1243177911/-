package com.online.controller;

import com.online.common.pojo.EasyUITreeNode;
import com.online.common.pojo.TaotaoResult;
import com.online.service.ContentCatgoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author iu
 */
@Controller
@RequestMapping("/content/category")
public class ContentCatgoryController {

    @Autowired
    private ContentCatgoryService contentCatgoryService;

    @RequestMapping("/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam( value = "id",defaultValue = "0")Long parentId){
        List<EasyUITreeNode> contentCatList = contentCatgoryService.getContentCatList(parentId);
        return contentCatList;

    }
    @RequestMapping("/create")
    @ResponseBody
    public TaotaoResult insertCatgory(Long parentId,String name){
        return contentCatgoryService.insertCatgory(parentId, name);
    }
    @RequestMapping("/update")
    public TaotaoResult updateCatgoryNode(Long id,String name){
        return  contentCatgoryService.updateCatgoryName(id,name);
    }

    @RequestMapping("/delete")
    public TaotaoResult deleteCatgoryNode(Long id){
        return contentCatgoryService.deleteCatgoryNode(id);
    }



}
