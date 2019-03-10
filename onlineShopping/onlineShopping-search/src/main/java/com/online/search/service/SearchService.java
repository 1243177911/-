package com.online.search.service;

import com.online.search.pojo.SearchResult;

public interface SearchService {

    SearchResult search(String queryString, int page, int rows) throws Exception;
}
