package com.online.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

public class SolrJTest {

    @Test
    public void solrJTest() throws IOException, SolrServerException {
        //创建连接
        SolrServer solrServer = new HttpSolrServer("http://192.168.133.131:8080/solr");
        //创建一个文档对象
        SolrInputDocument document = new SolrInputDocument();
        //添加域
        document.addField("id","solrtest01");
        document.addField("title","测试商品");
        document.addField("sell_point","你好");
        //添加到索引库
        solrServer.add(document);
        //提交
        solrServer.commit();
    }

    @Test
    public void queryTest() throws SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://192.168.133.131:8080/solr");
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        QueryResponse response = solrServer.query(query);
        SolrDocumentList results = response.getResults();
        for(SolrDocument solrDocument : results){
            System.out.println(solrDocument.get("id"));
            System.out.println(solrDocument.get("title"));
            System.out.println(solrDocument.get("sell_point"));
        }

    }
}
