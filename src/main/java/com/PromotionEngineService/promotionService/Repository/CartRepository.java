package com.PromotionEngineService.promotionService.Repository;

import com.PromotionEngineService.promotionService.Entity.Cart;
import com.PromotionEngineService.promotionService.Entity.Product;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByProductName(String name);

    //Cart findByProduct(Product product);

    /*@Modifying
    @Query("update Cart c set u.product_count = ?1, u.lastname = ?2 where u.id = ?3")
    void updateCartDeatils(List<Cart> cartList);*/
}
