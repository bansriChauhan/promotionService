package com.PromotionEngineService.promotionService.Service;

import com.PromotionEngineService.promotionService.Entity.Cart;
import com.PromotionEngineService.promotionService.Entity.Product;
import com.PromotionEngineService.promotionService.Repository.CartRepository;
import com.PromotionEngineService.promotionService.Repository.ProductRepository;
import com.PromotionEngineService.promotionService.Service.Impl.CartServiceImpl;
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
import static org.mockito.Mockito.when;


public class TestCartServiceImpl {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartServiceImpl cartService;
    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        //Mockito.framework().clearInlineMocks();
    }


    @Test
    public void testAddProduct() {
        Cart cart = new Cart();
        Product product = new Product();
        product.setName("A");
        cart.setProduct(product);
        cart.setQuantity(5);

        when(productRepository.findById("A")).thenReturn(Optional.of(product));

        when(cartRepository.findAll()).thenReturn(Collections.emptyList());
        Cart result = cartService.addProduct(cart);
        assertNotNull(result);
        assertEquals("A", result.getProduct().getName());
        assertEquals(5, result.getQuantity());

        Mockito.verify(productRepository).findById("A");

        Mockito.verify(cartRepository).findAll();
    }


    @Test
    public void testRemoveProduct() {
        Cart cart = new Cart();
        cart.setId(1L);
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        cartService.removeProduct(cart);
        Mockito.verify(cartRepository).findById(1L);
        Mockito.verify(cartRepository).deleteById(1L);
    }

    @Test
    public void testUpdateCart() {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setQuantity(5);
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        Cart updatedCart = new Cart();
        updatedCart.setId(1L);
        updatedCart.setQuantity(3);
        List<Cart> updatedCartList = new ArrayList<>();
        updatedCartList.add(updatedCart);
        List<Cart> updatedList = cartService.updateCart(updatedCartList);
        Mockito.verify(cartRepository).findById(1L);
        Mockito.verify(cartRepository).save(cart);
        assertEquals(8, updatedList.get(0).getQuantity());

    }

    @Test
    public void testGetCart() {
        List<Cart> cartList = new ArrayList<>();
        Cart cart1 = new Cart();
        cart1.setId(1L);
        cart1.setQuantity(5);
        cartList.add(cart1);
        Cart cart2 = new Cart();
        cart2.setId(2L);
        cart2.setQuantity(3);
        cartList.add(cart2);

        when(cartRepository.findAll()).thenReturn(cartList);
        List<Cart> returnedCartList = cartService.getCart();
        Mockito.verify(cartRepository).findAll();
        assertEquals(2, returnedCartList.size());
        assertTrue(returnedCartList.contains(cart1));
        assertTrue(returnedCartList.contains(cart2));
    }
}
