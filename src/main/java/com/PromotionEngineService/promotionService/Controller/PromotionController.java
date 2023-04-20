package com.PromotionEngineService.promotionService.Controller;

import com.PromotionEngineService.promotionService.Entity.Product;
import com.PromotionEngineService.promotionService.Entity.Promotion;
import com.PromotionEngineService.promotionService.Exception.ResourceNotFoundException;
import com.PromotionEngineService.promotionService.Service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/promotion")
public class PromotionController {
    @Autowired
    PromotionService promotionService;

    @PostMapping("/add")
    public List<Promotion> savePromotion(@RequestBody List<Promotion> promotionList){
        return promotionService.savePromotion(promotionList);
    }

    @PutMapping("/updatePromotion")
    public List<Promotion> updateCart(@RequestBody List<Promotion> promotionList){
        if(promotionList.isEmpty()){
            throw new ResourceNotFoundException("Please Enter Promotion Data!!");
        }
        List<Promotion> promotion=promotionService.updateProduct(promotionList);
        return promotion;
    }

    @GetMapping("/get-all")
    public List<Promotion> getAllPromotion(){
        return promotionService.getAllPromotion();
    }

    @GetMapping("/get/{productName}")
    public Promotion getProduct(@PathVariable("productName") String productName){

        if(productName==null){
            throw new ResourceNotFoundException("Please provide correct product Name!!");
        }
        return promotionService.getPromotion(productName);
    }

    @DeleteMapping("/deletePromotion/{productName}")
    public void deleteProduct(@PathVariable("productName") String productName){
        if(productName==null){
            throw new ResourceNotFoundException("Please Enter Product Name!!");
        }
        promotionService.deleteProduct(productName);
        //return ResponseEntity.noContent().build();
    }
}
