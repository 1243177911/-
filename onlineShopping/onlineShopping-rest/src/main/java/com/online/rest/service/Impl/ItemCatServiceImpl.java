package com.online.rest.service.Impl;

import com.alibaba.druid.util.StringUtils;
import com.online.common.pojo.TaotaoResult;
import com.online.common.utils.JsonUtils;
import com.online.mapper.TbItemCatMapper;
import com.online.pojo.TbItemCat;
import com.online.pojo.TbItemCatExample;
import com.online.rest.component.JedisClient;
import com.online.rest.pojo.CatNode;
import com.online.rest.pojo.ItemCatResult;
import com.online.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_CAT_KEY}")
    private String REDIS_CAT_KEY;

    @Value("${REDIS_CAT_KEY_ITEM}")
    private String REDIS_CAT_KEY_ITEM;

    @Override
    public ItemCatResult getItemCatList() {

        try {
            String json = jedisClient.hget(REDIS_CAT_KEY, REDIS_CAT_KEY_ITEM);
            if(!StringUtils.isEmpty(json)) {
                ItemCatResult itemCatResult = JsonUtils.jsonToPojo(json, ItemCatResult.class);
                return itemCatResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List list = getList(0L);
        ItemCatResult itemCatResult = new ItemCatResult();
        itemCatResult.setData(list);

        try{
            jedisClient.hset(REDIS_CAT_KEY,REDIS_CAT_KEY_ITEM,JsonUtils.objectToJson(itemCatResult));
        }catch (Exception e){
            e.printStackTrace();
        }
        return itemCatResult;
    }

    @Override
    public TaotaoResult syncItemCat() {
        Long hdel = jedisClient.hdel(REDIS_CAT_KEY, REDIS_CAT_KEY_ITEM);
        return TaotaoResult.ok();
    }

    //递归方法，根据parent查询一个树形列表
    private List getList(Long parentId) {
        //设置条件
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //根据parentId查询列表
        List<TbItemCat> tbItemCats = itemCatMapper.selectByExample(example);
        List result = new ArrayList();
        int index = 0;
        for (TbItemCat tbItemCat : tbItemCats) {
            if (index < 14) {
                if (tbItemCat.getIsParent()) {
                    CatNode node = new CatNode();
                    node.setUrl("/products/" + tbItemCat.getId() + ".html");
                    if (parentId == 0) {
                        node.setName("<a href='" + node.getUrl() + "'>" + tbItemCat.getName() + "</a>");
                        index++;
                    } else {
                        node.setName(tbItemCat.getName());
                    }
                    node.setItems(getList(tbItemCat.getId()));
                    result.add(node);
                } else {
                    String node = "/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName();
                    result.add(node);
                }
            } else {
                break;
            }
        }
        return result;
    }
}
