package com.online.rest.service.Impl;

import com.online.common.utils.JsonUtils;
import com.online.mapper.TbItemDescMapper;
import com.online.mapper.TbItemMapper;
import com.online.mapper.TbItemParamItemMapper;
import com.online.pojo.TbItem;
import com.online.pojo.TbItemDesc;
import com.online.pojo.TbItemParamItem;
import com.online.rest.component.JedisClient;
import com.online.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value(("${REDIS_ITEM_KEY}"))
    private String REDIS_ITEM_KEY;

    @Value(("${ITEM_BASE_INFO_KEY}"))
    private String ITEM_BASE_INFO_KEY;

    @Value(("${ITEM_DESC_KEY}"))
    private String ITEM_DESC_KEY;

    @Value(("${ITEM_PARAM_KEY}"))
    private String ITEM_PARAM_KEY;

    @Value("${ITEM_EXPIRE_SECOND}")
    private Integer ITEM_EXPIRE_SECOND;


    @Override
    public TbItem getItemById(Long itemId) {

        //查询缓存
        try{
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + ITEM_BASE_INFO_KEY + ":" + itemId);
            if(!StringUtils.isEmpty(json)){
                TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
                return item;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //根据商品id查询商品基本信息
        TbItem item = itemMapper.selectByPrimaryKey(itemId);

        //添加缓存()
        try{
            jedisClient.set(REDIS_ITEM_KEY + ":" + ITEM_BASE_INFO_KEY + ":" + itemId, JsonUtils.objectToJson(item));
            //设置过期时间（只能在key上面设置）
            jedisClient.expire(REDIS_ITEM_KEY + ":" + ITEM_BASE_INFO_KEY + ":" + itemId,ITEM_EXPIRE_SECOND);
        }catch (Exception e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public TbItemDesc getItemDescById(Long itemId) {
        //查询缓存
        try{
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + ITEM_DESC_KEY + ":" + itemId);
            if(!StringUtils.isEmpty(json)){
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return itemDesc;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);

        //添加缓存()
        try{
            jedisClient.set(REDIS_ITEM_KEY + ":" + ITEM_DESC_KEY + ":" + itemId, JsonUtils.objectToJson(itemDesc));
            //设置过期时间（只能在key上面设置）
            jedisClient.expire(REDIS_ITEM_KEY + ":" + ITEM_DESC_KEY + ":" + itemId,ITEM_EXPIRE_SECOND);
        }catch (Exception e){
            e.printStackTrace();
        }
        return itemDesc;
    }

    @Override
    public TbItemParamItem getItemParamItemById(Long itemId) {
        //查询缓存
        try{
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + ITEM_PARAM_KEY + ":" + itemId);
            if(!StringUtils.isEmpty(json)){
                TbItemParamItem tbItemParamItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
                return tbItemParamItem;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //判断条件
        TbItemParamItem itemParamItem = itemParamItemMapper.getItemParamItemByItemId(itemId);

        //添加缓存()
        try{
            jedisClient.set(REDIS_ITEM_KEY + ":" + ITEM_PARAM_KEY + ":" + itemId, JsonUtils.objectToJson(itemParamItem));
            //设置过期时间（只能在key上面设置）
            jedisClient.expire(REDIS_ITEM_KEY + ":" + ITEM_PARAM_KEY + ":" + itemId,ITEM_EXPIRE_SECOND);
        }catch (Exception e){
            e.printStackTrace();
        }
        return itemParamItem;
    }
}
