package com.online.service;

import com.online.common.pojo.EasyUIDataGridResult;
import com.online.common.pojo.TaotaoResult;
import com.online.pojo.TbContent;


/**
 * @author iu
 */
public interface ContentService {

    EasyUIDataGridResult getContentList(Long categoryId, int page, int rows);
    TaotaoResult insertContent(TbContent tbContent);

}
