package com.online.portal.service;

import com.online.portal.pojo.SearchResult;

public interface SearchService {
    SearchResult search(String keyword,int page, int rows);
}
