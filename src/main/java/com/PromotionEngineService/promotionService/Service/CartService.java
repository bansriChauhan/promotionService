package com.PromotionEngineService.promotionService.Service;

import com.PromotionEngineService.promotionService.Entity.Cart;
import com.PromotionEngineService.promotionService.Entity.Product;

import java.util.List;

public interface CartService {
    Cart addProduct(Cart cart);

    void removeProduct(Cart cart);

    List<Cart> updateCart(List<Cart> cartList);

    List<Cart> getCart();
}
