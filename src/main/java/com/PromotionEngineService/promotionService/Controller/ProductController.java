package com.PromotionEngineService.promotionService.Controller;

import com.PromotionEngineService.promotionService.Entity.Product;
import com.PromotionEngineService.promotionService.Exception.ResourceNotFoundException;
import com.PromotionEngineService.promotionService.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService service;

    @PostMapping("/add")
    public void addProduct(@RequestBody List<Product> productList) {

        if(productList.isEmpty()){
            throw new ResourceNotFoundException("Product is Empty!!");
        }
        service.addProduct(productList);
    }
    @GetMapping("/get-all")
    public List<Product> getAllProduct(){
        return service.getAllProduct();
    }
    @GetMapping("/get/{productName}")
    public Optional<Product> getProduct(@PathVariable("productName") String productName){

        if(productName==null){
            throw new ResourceNotFoundException("Please provide correct ID!!");
        }
        return service.getProduct(productName);
    }
    @PutMapping("/updateProduct")
    public List<Product> updateCart(@RequestBody List<Product> productList){
        if(productList.isEmpty()){
            throw new ResourceNotFoundException("Please Enter Product Data!!");
        }
        List<Product> products=service.updateProduct(productList);
        return products;
    }

    @DeleteMapping("/deleteProduct/{id}")
    public void deleteProduct(@PathVariable("id") String productName){
        if(productName==null){
            throw new ResourceNotFoundException("Please Enter Product Data!!");
        }
        service.deleteProduct(productName);
        //return ResponseEntity.noContent().build();
    }

}
