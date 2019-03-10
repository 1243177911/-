package com.online.rest.service.Impl;

import com.alibaba.druid.util.StringUtils;
import com.online.common.pojo.TaotaoResult;
import com.online.common.utils.JsonUtils;
import com.online.mapper.TbContentMapper;
import com.online.pojo.TbContent;
import com.online.pojo.TbContentExample;
import com.online.rest.component.JedisClient;
import com.online.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @author iu
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_CONTENT_KEY}")
    private String REDIS_CONTENT_KEY;

    @Override
    public List<TbContent> getContentList(Long cid) {
        //添加缓存
        //查询数据库之前查询缓存,如果又直接返回。
        try{
            String json = jedisClient.hget(REDIS_CONTENT_KEY, cid + "");
            if(!StringUtils.isEmpty(json)){
                List<TbContent> tbContents = JsonUtils.jsonToList(json, TbContent.class);
                return tbContents;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //根据cid查询内容列表
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> tbContents = contentMapper.selectByExampleWithBLOBs(example);

        //向缓存中添加缓存数据(不能影响正常得业务逻辑)
        try{
            //规范key 使用hSet ,定义一个保存内容的key,hash中每个项就是cid，value是list（json数据）
            jedisClient.hset(REDIS_CONTENT_KEY,cid+"", JsonUtils.objectToJson(tbContents));
        }catch (Exception e){
            e.printStackTrace();
        }
        return tbContents;
    }

    @Override
    public TaotaoResult syncContent(Long cid) {
        jedisClient.hdel(REDIS_CONTENT_KEY,cid+"");
        return TaotaoResult.ok();
    }
}
