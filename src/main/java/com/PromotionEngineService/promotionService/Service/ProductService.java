package com.PromotionEngineService.promotionService.Service;

import com.PromotionEngineService.promotionService.Entity.Cart;
import com.PromotionEngineService.promotionService.Entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    void addProduct(List<Product> productList);

    List<Product> getAllProduct();

    Optional<Product> getProduct(String productName);

    List<Product> updateProduct(List<Product> productList);

    void deleteProduct( String productName);

}
