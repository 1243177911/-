package com.online.service;

import com.online.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * @author iu
 */
public interface ItemCatService {
    List<EasyUITreeNode> getItemCatList(Long parentId);
}
