package com.PromotionEngineService.promotionService.Service.Impl;

import com.PromotionEngineService.promotionService.Entity.Cart;
import com.PromotionEngineService.promotionService.Entity.Product;
import com.PromotionEngineService.promotionService.Exception.ResourceNotFoundException;
import com.PromotionEngineService.promotionService.Repository.CartRepository;
import com.PromotionEngineService.promotionService.Repository.ProductRepository;
import com.PromotionEngineService.promotionService.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {


    @Autowired
    CartRepository repository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public Cart addProduct(Cart cart) {

        Product p = productRepository.findById(cart.getProduct().getName())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"+cart.getProduct().getName()));

        List<Cart> cartV=repository.findAll();
        Cart c = new Cart();
        c.setProduct(cart.getProduct());

        Optional<Cart> existingItemOptional = cartV.stream().filter(item -> item.getProduct().getName().equals(cart.getProduct().getName())).findFirst();

       if (!existingItemOptional.isEmpty()) {
            c.setQuantity((existingItemOptional.get().getQuantity())+cart.getQuantity());
        } else {
            c.setQuantity(cart.getQuantity());
        }
        repository.save(c);
        return c;
    }

    @Override
    public void removeProduct(Cart cart) {
        Cart existingCartt = repository.findById(cart.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id "+cart.getId()));

        repository.deleteById(existingCartt.getId());


    }

    @Override
    public List<Cart> updateCart(List<Cart> cartList) {
        List<Cart> updatedList = new ArrayList<>();
        for (Cart updatedCart: cartList) {
            Cart existingProduct = repository.findById(updatedCart.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + updatedCart.getId()));
            if (existingProduct!=null) {
                existingProduct.setQuantity((existingProduct.getQuantity()+updatedCart.getQuantity()));
            } else {
                existingProduct.setQuantity((updatedCart.getQuantity()));
            }
            existingProduct.setQuantity(updatedCart.getQuantity());
            existingProduct.setProduct(updatedCart.getProduct());

            Cart savedCart = repository.save(existingProduct);
            updatedList.add(savedCart);
        }
        return updatedList;
    }


    @Override
    public List<Cart> getCart() {
        List<Cart> cartList =repository.findAll();
        return cartList;
    }

}
