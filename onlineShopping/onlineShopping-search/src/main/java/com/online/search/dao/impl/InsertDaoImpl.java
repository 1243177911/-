package com.online.search.dao.impl;

import com.online.common.pojo.TaotaoResult;
import com.online.mapper.TbItemMapper;
import com.online.pojo.TbItem;
import com.online.search.dao.InsertDao;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InsertDaoImpl implements InsertDao {

    @Autowired
    private SolrServer solrServer;

    @Autowired
    private TbItemMapper tbItemMapper;

    @Override
    public TaotaoResult insert(Long id) throws Exception {
        //查询数据库获得插入对象
        TbItem item = tbItemMapper.selectByPrimaryKey(id);
        //更新添加索引库
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id",item.getId());
        document.setField("item_title",item.getTitle());
        document.setField("item_price",item.getPrice());
        document.setField("item_image",item.getImage());
        document.setField("item_category_name",item.getCid());
        document.setField("item_sell_point",item.getSellPoint());
        solrServer.add(document);
        solrServer.commit();
        return TaotaoResult.ok();
    }
}
