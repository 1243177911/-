package com.online.rest.service;

import com.online.common.pojo.TaotaoResult;
import com.online.pojo.TbContent;

import java.util.List;

public interface ContentService {
    //得到portal轮播图数据
    List<TbContent> getContentList(Long cid);

    //缓存同步，后台修改数据库后redis中删除缓存
    TaotaoResult syncContent(Long cid);
}
