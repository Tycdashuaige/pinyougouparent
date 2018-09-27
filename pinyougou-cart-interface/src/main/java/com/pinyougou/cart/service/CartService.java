package com.pinyougou.cart.service;

import com.pinyougou.pojogroup.Cart;

import java.util.List;

/**
 * @author tangyucong
 * @Title: CartService
 * @ProjectName pinyougouparent
 * @Description: TODO
 * @date 2018/9/2310:44
 */
public interface CartService {
    public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num);

    public List<Cart> findCartListFromRedis(String username);

    public void saveCartListToRedis(String username,List<Cart> cartList);

    public List<Cart> mergeCartList(List<Cart> cartList1,List<Cart> cartList2);
}
