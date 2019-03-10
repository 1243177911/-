package com.online.service.impl;

import com.online.common.pojo.EasyUIDataGridResult;
import com.online.common.pojo.TaotaoResult;
import com.online.mapper.TbContentMapper;
import com.online.pojo.TbContent;
import com.online.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author iu
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Override
    public EasyUIDataGridResult getContentList(Long categoryId, int page, int rows) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (page == 1) {
            map.put("page", page-1);
            map.put("rows", rows);
        }else {
            map.put("page", page*rows);
            map.put("rows", rows);
        }
        map.put("categoryId", categoryId);
        //得到当前categoryId的所有数据
        List<TbContent> contentList = contentMapper.getContentList(map);
        //得到查询出的总记录数
        long total = contentMapper.getContentTotal(categoryId);

        EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
        easyUIDataGridResult.setTotal(total);
        easyUIDataGridResult.setRows(contentList);
        return easyUIDataGridResult;
    }

    //添加content数据
    @Override
    public TaotaoResult insertContent(TbContent tbContent) {
        tbContent.setUpdated(new Date());
        tbContent.setCreated(new Date());
        contentMapper.insert(tbContent);
        return TaotaoResult.ok();
    }

}
