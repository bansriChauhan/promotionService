package com.PromotionEngineService.promotionService.Service.Impl;

import com.PromotionEngineService.promotionService.Entity.Product;
import com.PromotionEngineService.promotionService.Exception.ResourceNotFoundException;
import com.PromotionEngineService.promotionService.Repository.ProductRepository;
import com.PromotionEngineService.promotionService.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository repository;

    @Override
    public void addProduct(List<Product> productList) {
        repository.saveAll(productList);
    }

    @Override
    public List<Product> getAllProduct() {
        List<Product> productList =repository.findAll();
        return productList;
    }

    @Override
    public Optional<Product> getProduct(String productName) {
        Product existingProduct = repository.findById(productName)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productName));
        Optional<Product> product=repository.findById(productName);
        return product;
    }
    @Override
    public List<Product> updateProduct(List<Product> productList) {
        List<Product> updatedList = new ArrayList<>();
        for (Product updatedProduct : productList) {
            Product existingProduct = repository.findById(updatedProduct.getName())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + updatedProduct.getName()));

            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());

            Product savedProduct = repository.save(existingProduct);
            updatedList.add(savedProduct);
        }
        return updatedList;
    }

    @Override
    public void deleteProduct( String productName) {
        Product existingProduct = repository.findById(productName)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productName));

        repository.deleteById(productName);

    }
}
