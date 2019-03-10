package com.online.portal.service.impl;

import com.online.common.pojo.TaotaoResult;
import com.online.common.utils.HttpClientUtil;
import com.online.common.utils.JsonUtils;
import com.online.pojo.TbItem;
import com.online.pojo.TbItemDesc;
import com.online.pojo.TbItemParamItem;
import com.online.portal.pojo.PortalItem;
import com.online.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {

    @Value("${REST_ITEM_BASE_URL}")
    private String REST_ITEM_BASE_URL;

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${REST_ITEM_DESC_URL}")
    private String REST_ITEM_DESC_URL;

    @Value("${REST_ITEM_PARAM_URL}")
    private String REST_ITEM_PARAM_URL;

    @Override
    public TbItem getItemById(Long itemId) {

        //根据商品id查询商品基本信息
        String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_BASE_URL + itemId);
        //json转java
        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, PortalItem.class);
        //取商品对象
        TbItem tbItem = (TbItem) taotaoResult.getData();

        return tbItem;
    }

    @Override
    public String getItemDescById(Long itemId) {
        String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_DESC_URL + itemId);
        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItemDesc.class);
        TbItemDesc itemDesc = (TbItemDesc)taotaoResult.getData();
        String desc = itemDesc.getItemDesc();
        return desc;
    }

    @Override
    public String getItemParamById(Long itemId) {
        String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_PARAM_URL + itemId);
        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItemParamItem.class);
        TbItemParamItem itemParamItem = (TbItemParamItem)taotaoResult.getData();
        String paramData = itemParamItem.getParamData();
        //把paramData 从json转java对象
        List<Map> paramList = JsonUtils.jsonToList(paramData, Map.class);
        StringBuffer sb = new StringBuffer();

        sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
        sb.append("	<tbody>\n");

        for (Map map : paramList) {
            sb.append("		<tr>\n");
            sb.append("			<th class=\"tdTitle\" colspan=\"2\">" + map.get("group") + "</th>\n");
            sb.append("		</tr>\n");

            List<Map> mapList2 = (List<Map>) map.get("params");

            for (Map map2 : mapList2) {
                sb.append("		<tr>\n");
                sb.append("			<td class=\"tdTitle\">" + map2.get("k") + "</td>\n");
                sb.append("			<td>" + map2.get("v") + "</td>\n");
                sb.append("		</tr>\n");
            }
        }

        sb.append("	</tbody>\n");
        sb.append("</table>");

        return sb.toString();
    }
}
