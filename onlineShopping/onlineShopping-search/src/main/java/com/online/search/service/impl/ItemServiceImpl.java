package com.online.search.service.impl;

import com.online.common.pojo.TaotaoResult;
import com.online.search.mapper.ItemMapper;
import com.online.search.pojo.SearchItem;
import com.online.search.service.ItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private SolrServer solrServer;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public TaotaoResult importItems() throws Exception{
        List<SearchItem> itemList = itemMapper.getItemList();
        for(SearchItem searchItem : itemList){
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id",searchItem.getId());
            document.addField("item_title",searchItem.getTitle());
            document.addField("item_sell_point",searchItem.getSell_point());
            document.addField("item_price",searchItem.getPrice());
            document.addField("item_image",searchItem.getImage());
            document.addField("item_category_name",searchItem.getCategory_name());
            document.addField("item_desc",searchItem.getItem_desc());
            solrServer.add(document);
        }
        solrServer.commit();
        return TaotaoResult.ok();
    }
}
