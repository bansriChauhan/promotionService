package com.PromotionEngineService.promotionService.Controller;

import com.PromotionEngineService.promotionService.Entity.Cart;
import com.PromotionEngineService.promotionService.Entity.Product;
import com.PromotionEngineService.promotionService.Service.CartService;
import com.PromotionEngineService.promotionService.Service.ProductService;
import com.PromotionEngineService.promotionService.Service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    CartService service;

    @Autowired
    PromotionService promotionService;

    @PostMapping("/add")
    public Cart addCart(@RequestBody Cart cart){
        return service.addProduct(cart);
    }

    @PostMapping("/delete")
    public void deleteCart(@RequestBody Cart cart){
         service.removeProduct(cart);
    }

    @GetMapping("/get")
    public List<Cart> getCart(){
        return service.getCart();
    }


    @PutMapping("/update")
    public List<Cart> updateCart(@RequestBody List<Cart> cartList){
        return service.updateCart(cartList);
    }

    @GetMapping("/totalPrice")
    public double getTotalPrice(@RequestBody List<Cart> cartList){
        return promotionService.getTotalPrice(cartList);
    }
}
