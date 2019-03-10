package com.online.search.dao;

import com.online.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;

public interface SearchDao {

    SearchResult search(SolrQuery solrQuery) throws Exception;
}
