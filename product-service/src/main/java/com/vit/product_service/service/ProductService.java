package com.vit.product_service.service;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vit.product_service.dto.ProductRequest;
import com.vit.product_service.dto.ProductResponse;
import com.vit.product_service.model.Product;
import com.vit.product_service.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product); 
        log.info("Product {} is saved", product.getId());
    }
public List<ProductResponse> getAllProducts() {
    try {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::mapToProductResponse)
                .toList();
    } catch (Exception e) {
        log.error("Error while fetching products. Error error hai: {}", e.getMessage());
        // You can also return an empty list or handle the error in another way
        return Collections.emptyList();
    }
}

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
