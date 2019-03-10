package com.online.service;

import com.online.common.pojo.EasyUITreeNode;
import com.online.common.pojo.TaotaoResult;

import java.util.List;

/**
 * @author iu
 */
public interface ContentCatgoryService {

    List<EasyUITreeNode> getContentCatList(Long parentId);
    TaotaoResult insertCatgory(Long parentId,String name);
    TaotaoResult updateCatgoryName(Long id,String name);
    TaotaoResult deleteCatgoryNode(Long id);

}
