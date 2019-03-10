package com.online.service.impl;

import com.online.common.pojo.EasyUITreeNode;
import com.online.mapper.TbItemCatMapper;
import com.online.pojo.TbItemCat;
import com.online.pojo.TbItemCatExample;
import com.online.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author iu
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Override
    public List<EasyUITreeNode> getItemCatList(Long parentId) {

        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        List<EasyUITreeNode> result = new ArrayList<EasyUITreeNode>();
        for(TbItemCat tbItemCat : list){
            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setId(tbItemCat.getId());
            easyUITreeNode.setState(tbItemCat.getIsParent()?"closed":"open");
            easyUITreeNode.setText(tbItemCat.getName());
            result.add(easyUITreeNode);
        }
        return result;
    }
}
