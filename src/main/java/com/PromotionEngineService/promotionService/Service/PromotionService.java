package com.PromotionEngineService.promotionService.Service;

import com.PromotionEngineService.promotionService.Entity.Cart;
import com.PromotionEngineService.promotionService.Entity.Product;
import com.PromotionEngineService.promotionService.Entity.Promotion;

import java.util.List;
import java.util.Optional;

public interface PromotionService {
    List<Promotion> savePromotion(List<Promotion> promotionList);

    List<Promotion> updateProduct(List<Promotion> promotionList);

    List<Promotion> getAllPromotion();

   Promotion getPromotion(String productName);

    void deleteProduct(String productName);

    double getTotalPrice(List<Cart> cartList);
}
