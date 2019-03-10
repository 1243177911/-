package com.online.search.service.impl;

import com.online.search.dao.SearchDao;
import com.online.search.pojo.SearchResult;
import com.online.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;

    @Override
    public SearchResult search(String queryString, int page, int rows) throws Exception {
        //创建查询条件
        SolrQuery solrQuery = new SolrQuery();

        //设置查询条件
        solrQuery.setQuery(queryString);

        //设置分页条件
        solrQuery.setStart((page-1)*rows);
        solrQuery.setRows(rows);

        //设置默认搜索域
        solrQuery.set("df","item_title");

        //设置高亮
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<font class=\"skcolor_ljg\">");
        solrQuery.setHighlightSimplePost("</font>");
        
        //执行查询
        SearchResult searchResult = searchDao.search(solrQuery);
        Long recordCount = searchResult.getRecordCount();
        //取出总页数
        long pageCount = recordCount / rows;
        if(recordCount % rows >0){
            pageCount++;
        }
        searchResult.setPageCount(pageCount);
        //设置当前页数
        searchResult.setCurPage(page);
        return searchResult;
    }
}
