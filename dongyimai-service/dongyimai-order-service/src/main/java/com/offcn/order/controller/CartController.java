package com.offcn.order.controller;

import com.offcn.entity.Result;
import com.offcn.entity.StatusCode;
import com.offcn.order.group.Cart;
import com.offcn.order.service.CartService;
import com.offcn.utils.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author chenxi
 * @date 2021/11/18 21:42
 * @description
 */
@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartController {


    @Autowired
    private CartService cartService;

    @Autowired
    private TokenDecode tokenDecode;


    @GetMapping("/findCartList")
    public List<Cart> findCartList(){
        String username = tokenDecode.getUserInfo().get("user_name");
        List<Cart> cartList = cartService.findCartListFromRedis(username);
        return cartList;

    }


    @PostMapping("/addGoodsToCartList")
    public Result addGoodsToCartList(Long itemId, Integer num){
        try {
            String username = tokenDecode.getUserInfo().get("user_name");
            List<Cart> cartList = cartService.findCartListFromRedis(username);
            List<Cart> addGoodsToCartList = cartService.addGoodsToCartList(cartList, itemId, num);
            cartService.saveCartListToRedis(username, addGoodsToCartList);
            return new Result(true, StatusCode.OK,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR,"添加失败");
        }


    }



}
