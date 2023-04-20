package com.PromotionEngineService.promotionService.Service;
import com.PromotionEngineService.promotionService.Entity.Cart;
import com.PromotionEngineService.promotionService.Entity.Product;
import com.PromotionEngineService.promotionService.Entity.Promotion;
import com.PromotionEngineService.promotionService.Exception.ResourceNotFoundException;
import com.PromotionEngineService.promotionService.Repository.CartRepository;
import com.PromotionEngineService.promotionService.Repository.ProductRepository;
import com.PromotionEngineService.promotionService.Repository.PromotionRepository;
import com.PromotionEngineService.promotionService.Service.Impl.CartServiceImpl;
import com.PromotionEngineService.promotionService.Service.Impl.PromotionServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
public class TestPromotionServiceImpl {
    @Mock
    private PromotionRepository promotionRepository;
    @InjectMocks
    private PromotionServiceImpl promotionService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        //Mockito.framework().clearInlineMocks();
    }

    @Test
    public void testSavePromotion() {
        List<Promotion> promotionList = new ArrayList<>();
        Promotion p=new Promotion();
        p.setCount(3);
        p.setProduct("A");
        p.setDiscount_price(130);

        when(promotionRepository.saveAll(promotionList)).thenReturn(promotionList);

        List<Promotion> savedPromotionList = promotionService.savePromotion(promotionList);

        assertEquals(promotionList.size(), savedPromotionList.size());
        Mockito.verify(promotionRepository, times(1)).saveAll(promotionList);
    }

    @Test
    public void testGetTotalPrice() {
        List<Cart> cartList = new ArrayList<>();
        Product p=new Product();
        p.setPrice(50.00);
        p.setName("A");
        Cart cart=new Cart();
        cart.setQuantity(5);
        cart.setProduct(p);
        cart.setId(1L);
        cartList.add(cart);

        List<Promotion> promotions = new ArrayList<>();
        Promotion promotion=new Promotion();
        promotion.setDiscount_price(130);
        promotion.setProduct("A");
        promotion.setCount(3);

        Mockito.when(promotionRepository.findAll()).thenReturn(promotions);

        double expectedTotalPrice = 800.0; // 2*300 + 2*100 + 1*200
        double actualTotalPrice = promotionService.getTotalPrice(cartList);

        assertEquals(expectedTotalPrice, actualTotalPrice, 0.01);
    }

    @Test
    public void testUpdateProduct() {
        List<Promotion> promotionList = new ArrayList<>();
        Promotion promotion=new Promotion();
        promotion.setDiscount_price(130);
        promotion.setProduct("A");
        promotion.setCount(3);
        promotionList.add(promotion);

        Mockito.when(promotionRepository.findById("A")).thenReturn(Optional.of(promotion));
        Mockito.when(promotionRepository.save(Mockito.any(Promotion.class))).thenAnswer(i -> i.getArguments()[0]);

        List<Promotion> updatedList = promotionService.updateProduct(promotionList);

        assertEquals(updatedList.size(), promotionList.size());
        assertEquals(updatedList.get(0).getProduct(), "A");
        assertEquals(updatedList.get(0).getDiscount_price(), 300);
        assertEquals(updatedList.get(0).getCount(), 3);
    }

    @org.junit.Test(expected = ResourceNotFoundException.class)
    public void testUpdateProduct_NotFound() {
        List<Promotion> promotionList = new ArrayList<>();
        Promotion promotion=new Promotion();
        promotion.setDiscount_price(130);
        promotion.setProduct("A");
        promotion.setCount(3);
        promotionList.add(promotion);

        Mockito.when(promotionRepository.findById("A")).thenReturn(Optional.empty());

        promotionService.updateProduct(promotionList);
    }
}
