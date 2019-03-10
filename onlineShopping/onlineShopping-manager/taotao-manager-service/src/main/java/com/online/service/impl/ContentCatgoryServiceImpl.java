package com.online.service.impl;

import com.online.common.pojo.EasyUITreeNode;
import com.online.common.pojo.TaotaoResult;
import com.online.mapper.TbContentCategoryMapper;
import com.online.pojo.TbContentCategory;
import com.online.pojo.TbContentCategoryExample;
import com.online.service.ContentCatgoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author iu
 */
@Service
public class ContentCatgoryServiceImpl implements ContentCatgoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCatList(Long parentId) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> tbContentCategories = contentCategoryMapper.selectByExample(example);
        List<EasyUITreeNode> resultList = new ArrayList<EasyUITreeNode>();
        for(TbContentCategory tbContentCategory : tbContentCategories){
            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setId(tbContentCategory.getId());
            easyUITreeNode.setText(tbContentCategory.getName());
            easyUITreeNode.setState(tbContentCategory.getIsParent()?"closed":"open");
            resultList.add(easyUITreeNode);
        }

        return resultList;
    }

    @Override
    public TaotaoResult insertCatgory(Long parentId, String name) {
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setParentId(parentId);
        tbContentCategory.setName(name);
        tbContentCategory.setIsParent(false);
//        '排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数',
        tbContentCategory.setSortOrder(1);
//        状态。可选值:1(正常),2(删除)',
        tbContentCategory.setStatus(1);
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setUpdated(new Date());
        contentCategoryMapper.insert(tbContentCategory);
        //取出返回的注解（用于更新id值）
        Long id = tbContentCategory.getId();
        //如果当前节点不是父节点，则设置为父节点
        TbContentCategory tbContentCategory1 = contentCategoryMapper.selectByPrimaryKey(parentId);
        if(!tbContentCategory1.getIsParent()){
            tbContentCategory1.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKey(tbContentCategory1);
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult updateCatgoryName(Long id,String name) {
        TbContentCategory tbContentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        tbContentCategory.setName(name);
        contentCategoryMapper.updateByPrimaryKey(tbContentCategory);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteCatgoryNode(Long id) {
        //通过id查询到当前节点的所有信息
        TbContentCategory tbContentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        //得到当前节点的父id
        Long parentId = tbContentCategory.getParentId();
        //判断是否父节点
        if(!tbContentCategory.getIsParent()){
            //非父节点，直接删除
            contentCategoryMapper.deleteByPrimaryKey(id);
            TbContentCategoryExample exampl = new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = exampl.createCriteria();
            criteria.andParentIdEqualTo(parentId);
            List<TbContentCategory> tbContentCategories = contentCategoryMapper.selectByExample(exampl);
            //判断当前父节点是否为，为0则设置父节点为叶子节点
            if(tbContentCategories.size() == 0){
                TbContentCategory tbContentCategoryParent = contentCategoryMapper.selectByPrimaryKey(parentId);
                tbContentCategoryParent.setIsParent(false);
                contentCategoryMapper.updateByPrimaryKey(tbContentCategoryParent);
            }
        }
        //当前节点为父节点
        else {
            deleteChildren(id);
        }
        return TaotaoResult.ok("remove");
    }

    //递归的方式删除子节点(parentId为删除节点的id)
    private void deleteChildren(Long parentId){
        //通过父节点的id找出子节点
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> tbContentCategories = contentCategoryMapper.selectByExample(example);
        //遍历tbContentCategories
        for(TbContentCategory tbContentCategory : tbContentCategories){
            //判断tbContentCategory是否为父节点
            if(tbContentCategory.getIsParent()){
                deleteChildren(tbContentCategory.getId());
            }
            //不为父节点，直接删除
            contentCategoryMapper.deleteByPrimaryKey(tbContentCategory.getId());
        }
        contentCategoryMapper.deleteByPrimaryKey(parentId);
    }
}
