package com.PromotionEngineService.promotionService.Service;

import com.PromotionEngineService.promotionService.Entity.Product;
import com.PromotionEngineService.promotionService.Exception.ResourceNotFoundException;
import com.PromotionEngineService.promotionService.Repository.CartRepository;
import com.PromotionEngineService.promotionService.Repository.ProductRepository;
import com.PromotionEngineService.promotionService.Service.Impl.CartServiceImpl;
import com.PromotionEngineService.promotionService.Service.Impl.ProductServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class TestProductServiceImpl {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetProduct() {

        Product product = new Product();
        product.setName("A");
        product.setPrice(50.0);

        when(productRepository.findById("A")).thenReturn(Optional.of(product));
        Optional<Product> returnedProduct = productService.getProduct("A");
        Mockito.verify(productRepository).findById("A");
        assertTrue(returnedProduct.isPresent());
        assertEquals(product, returnedProduct.get());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetProductNotFound() {

        when(productRepository.findById("T")).thenThrow(new ResourceNotFoundException("Product not found with id Nonexistent Product"));
        productService.getProduct("T");
    }

    @Test
    public void testUpdateProduct() {

        List<Product> productList = new ArrayList<>();
        Product p=new Product();
        p.setName("A");
        p.setPrice(50.00);
        productList.add(p);

        Product existingProduct1 = new Product();
        existingProduct1.setName("A");
        existingProduct1.setPrice(50.00);
        productList.add(existingProduct1);

        when(productRepository.findById("A")).thenReturn(Optional.of(existingProduct1));
        List<Product> updatedList = productService.updateProduct(productList);
        Assertions.assertEquals(2, updatedList.size());

        Product updatedProduct1 = updatedList.get(0);
        Assertions.assertEquals("A", updatedProduct1.getName());

    }


    @Test
    public void testDeleteProduct_success() {
        String productName = "Product1";
        Product existingProduct1 = new Product();
        existingProduct1.setName("A");
        existingProduct1.setPrice(50.00);
        when(productRepository.findById(productName)).thenReturn(Optional.of(existingProduct1));
        doNothing().when(productRepository).deleteById(productName);
        productService.deleteProduct(productName);
        Assertions.assertDoesNotThrow(() -> productRepository.findById(productName).orElseThrow());
    }

    @Test
    public void testDeleteProduct_productNotFound() {

        String productName = "A";
        when(productRepository.findById(productName)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(productName));
    }

    @Test
    public void testDeleteProduct_errorInDeletion() {

        String productName = "A";
        Product existingProduct1 = new Product();
        existingProduct1.setName("A");
        existingProduct1.setPrice(50.00);
        when(productRepository.findById(productName)).thenReturn(Optional.of(existingProduct1));
        doThrow(ResourceNotFoundException.class).when(productRepository).deleteById(productName);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(productName));
    }
}
