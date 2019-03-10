package com.online.portal.service.impl;

import com.online.common.pojo.TaotaoResult;
import com.online.common.utils.HttpClientUtil;
import com.online.common.utils.JsonUtils;
import com.online.pojo.TbContent;
import com.online.portal.pojo.AdNode;
import com.online.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**获得大广告位
 * @author iu
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${REST_CONTENT_URL}")
    private String REST_CONTENT_URL;

    @Value("${REST_CONTENT_AD1_CID}")
    private String REST_CONTENT_AD1_CID;

    @Override
    public String getAd1List() {
        //调取服务获得数据
        String json = HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_URL + REST_CONTENT_AD1_CID);
        //把json转换位java对象
        TaotaoResult taotaoResult = TaotaoResult.formatToList(json, TbContent.class);
        List<TbContent> list = (List<TbContent>)taotaoResult.getData();
        //设置一个list接收AdNode
        List<AdNode> resultList = new ArrayList<>();
        //遍历设置AdNode
        for(TbContent content : list){
            AdNode node = new AdNode();
            node.setHeight(270);
            node.setWidth(670);
            node.setSrc(content.getPic());

            node.setHeight(240);
            node.setWidth(550);
            node.setSrcB(content.getPic2());

            node.setAlt(content.getSubTitle());
            node.setHref(content.getUrl());

            resultList.add(node);
        }
        //把result转换位json
        String result = JsonUtils.objectToJson(resultList);
        return result;
    }
}
