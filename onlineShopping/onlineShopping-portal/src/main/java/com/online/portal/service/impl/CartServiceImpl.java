package com.online.portal.service.impl;

import com.online.common.pojo.TaotaoResult;
import com.online.common.utils.CookieUtils;
import com.online.common.utils.HttpClientUtil;
import com.online.common.utils.JsonUtils;
import com.online.pojo.TbItem;
import com.online.portal.pojo.CartItem;
import com.online.portal.service.CartService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车服务
 * @author iu
 */
@Service
public class CartServiceImpl implements CartService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;    
    
    @Value("${REST_ITEM_BASE_URL}")
    private String REST_ITEM_BASE_URL;

    @Override
    public TaotaoResult addCartItem(Long itemId, int num, HttpServletRequest request, HttpServletResponse response) {

        CartItem cartItem = null;
        //取购物车商品列表
        List<CartItem> cartItemList = getCartItemList(request);
        //购物车里已存在则数量加一
        for(CartItem cartItemExist : cartItemList){
            if(cartItemExist.getId()==itemId){
                cartItemExist.setNum(cartItemExist.getNum()+1);
                cartItem = cartItemExist;
                break;
            }
        }
        //不存在获取商品信息，并写入cookie中
        if(cartItem == null) {
            cartItem = new CartItem();
            //取商品信息
            String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_BASE_URL + itemId);
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItem.class);
            if (taotaoResult.getStatus() == 200) {
                TbItem item = (TbItem) taotaoResult.getData();
                cartItem.setId(item.getId());
                cartItem.setTitle(item.getTitle());
                //item.getImage 存了多张图片，使用 ，分割取第一张
                cartItem.setImage(item.getImage() == null ? "" : item.getImage().split(",")[0]);
                cartItem.setPrice(item.getPrice());
                cartItem.setNum(num);
            }
            //添加到购物车列表
            cartItemList.add(cartItem);
        }
        //把购物车列表写会cookie
        CookieUtils.setCookie(request,response,"TT_CART",JsonUtils.objectToJson(cartItemList),true);
        return TaotaoResult.ok();
    }

    /**
     * 从cookie中取购物车商品列表
     * @return
     */

    private List<CartItem> getCartItemList(HttpServletRequest request){
        String cartJson = CookieUtils.getCookieValue(request, "TT_CART", true);
        if(cartJson == null){
            //防止空指针
            return new ArrayList<>();
        }
        try {
            List<CartItem> cartItems = JsonUtils.jsonToList(cartJson, CartItem.class);
            return cartItems;
        }catch (Exception e){
            e.printStackTrace();
            //防止空指针
            return new ArrayList<>();
        }
    }
}
