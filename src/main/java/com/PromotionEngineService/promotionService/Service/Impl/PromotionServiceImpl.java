package com.PromotionEngineService.promotionService.Service.Impl;

import com.PromotionEngineService.promotionService.Entity.Cart;
import com.PromotionEngineService.promotionService.Entity.Promotion;
import com.PromotionEngineService.promotionService.Exception.ResourceNotFoundException;
import com.PromotionEngineService.promotionService.Repository.PromotionRepository;
import com.PromotionEngineService.promotionService.Service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    PromotionRepository repository;
    @Override
    public List<Promotion> savePromotion(List<Promotion> promotionList) {

        return repository.saveAll(promotionList);
    }

    @Override
    public double getTotalPrice(List<Cart> cartList){
        List<Promotion> p = repository.findAll();
        double final_Price = 0.0;
        double price=0.0;

        for(Promotion ps:p){

            if(ps.getProduct().length()>2 && cartList.stream().filter(e->ps.getProduct().contains(e.getProduct().getName())).count()>1){
                Cart c = cartList.stream().filter(e->ps.getProduct().contains(e.getProduct().getName())).min(Comparator.comparing(Cart::getQuantity)).get();
                price= c.getQuantity() * ps.getDiscount_price() ;
                Cart q = cartList.stream().filter(e->ps.getProduct().contains(e.getProduct().getName()) && !c.getProduct().getName().equals(e.getProduct().getName())).findFirst().get();
                price = price + ((q.getQuantity()-c.getQuantity()) * q.getProduct().getPrice());
            }else{
            Cart c= cartList.stream().filter(e->ps.getProduct().contains(e.getProduct().getName())).findFirst().get();

            if(c.getQuantity()<ps.getCount()){
                price = c.getQuantity()*c.getProduct().getPrice();
            }else if (c.getQuantity()==ps.getCount()){
                price= ps.getDiscount_price();
            }else{
                int prasds = c.getQuantity()/ps.getCount();
                price = (prasds*ps.getDiscount_price()) + ((c.getQuantity()%ps.getCount()) * c.getProduct().getPrice());
            }
            }
            final_Price = final_Price+price;

        }
        return final_Price;
    }
    @Override
    public List<Promotion> updateProduct(List<Promotion> promotionList) {
        List<Promotion> updatedList = new ArrayList<>();
        for (Promotion updatedPromotion : promotionList) {
            Promotion existingProduct = repository.findById(updatedPromotion.getProduct())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + updatedPromotion.getProduct()));

            existingProduct.setProduct(updatedPromotion.getProduct());
            existingProduct.setDiscount_price(updatedPromotion.getDiscount_price());
            existingProduct.setCount(updatedPromotion.getCount());

            Promotion savedPromotion = repository.save(existingProduct);
            updatedList.add(savedPromotion);
        }
        return updatedList;
    }

    @Override
    public List<Promotion> getAllPromotion() {
        List<Promotion> promotionList =repository.findAll();
        return promotionList;
    }

    @Override
    public Promotion getPromotion(String productName) {
        Promotion existingProduct = repository.findById(productName)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with name " + productName));
        return existingProduct;
    }

    @Override
    public void deleteProduct(String productName) {
        Promotion existingProduct = repository.findById(productName)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with name " + productName));

        repository.deleteById(productName);
    }
}
