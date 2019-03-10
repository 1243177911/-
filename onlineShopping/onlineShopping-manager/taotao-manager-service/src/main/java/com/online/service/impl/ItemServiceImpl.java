package com.online.service.impl;

import com.online.common.pojo.EasyUIDataGridResult;
import com.online.common.pojo.TaotaoResult;
import com.online.common.utils.IDUtils;
import com.online.common.utils.JsonUtils;
import com.online.mapper.TbItemDescMapper;
import com.online.mapper.TbItemMapper;
import com.online.mapper.TbItemParamItemMapper;
import com.online.pojo.TbItem;
import com.online.pojo.TbItemDesc;
import com.online.pojo.TbItemParamItem;
import com.online.pojo.TbItemParamItemExample;
import com.online.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Override
    public TbItem getItemByid(Long itemId) {
        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        return item;
    }

    @Override
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        Map<String, Object> data = new HashMap<String, Object>();
        if (page == 1) {
            data.put("page", page);
            data.put("rows", rows);
        } else {
            data.put("page", page * rows);
            data.put("rows", rows);
        }
        List<TbItem> list = itemMapper.getItemList(data);
        Long total = itemMapper.getItemTotal();
        EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
        easyUIDataGridResult.setRows(list);
        easyUIDataGridResult.setTotal(total);
        return easyUIDataGridResult;
    }

    @Override
    public TaotaoResult createItem(TbItem tbItem, String desc, String itemParams) {
        //生成商品ID，用时间（毫秒）+2位随机数
        Long id = IDUtils.genItemId();
        tbItem.setId(id);
        //'商品状态，1-正常，2-下架，3-删除',
        tbItem.setStatus((byte) 1);
        //添加更新，创建时间
        Date date = new Date();
        tbItem.setCreated(date);
        tbItem.setUpdated(date);
        itemMapper.insert(tbItem);
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        itemDesc.setItemId(id);
        itemDesc.setItemDesc(desc);
        itemDescMapper.insert(itemDesc);

        TbItemParamItem itemParamItem = new TbItemParamItem();
        itemParamItem.setItemId(id);
        itemParamItem.setParamData(itemParams);
        itemParamItem.setCreated(date);
        itemParamItem.setUpdated(date);
        itemParamItemMapper.insert(itemParamItem);

        return TaotaoResult.ok();
    }

    @Override
    public String getItemParamHtml(long id) {
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(id);
        List<TbItemParamItem> tbItemParamItems = itemParamItemMapper.selectByExampleWithBLOBs(example);
        if (tbItemParamItems == null || tbItemParamItems.isEmpty()) {
            return "";
        }
        TbItemParamItem itemParamItem = tbItemParamItems.get(0);
        String paramData = itemParamItem.getParamData();
        List<Map> ts = JsonUtils.jsonToList(paramData, Map.class);
        StringBuffer sb = new StringBuffer();

        sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
        sb.append("	<tbody>\n");

        for (Map map : ts) {
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
