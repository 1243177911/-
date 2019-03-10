package com.online.portal.service;

import com.online.common.pojo.TaotaoResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CartService {

    TaotaoResult addCartItem(Long itemId, int num, HttpServletRequest request, HttpServletResponse response);
}
