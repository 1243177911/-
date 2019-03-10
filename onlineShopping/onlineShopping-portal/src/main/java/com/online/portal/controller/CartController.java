package com.online.portal.controller;

import com.online.common.pojo.TaotaoResult;
import com.online.portal.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 购物车
 * @author iu
 */
@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping("/cart/add/{itemId}")
    public String addCartItem(@PathVariable long itemId,
                              @RequestParam(defaultValue = "1") Integer num,
                              HttpServletResponse response,
                              HttpServletRequest request){
        TaotaoResult taotaoResult = cartService.addCartItem(itemId, num, request, response);
        return "cart-success";
    }
}
