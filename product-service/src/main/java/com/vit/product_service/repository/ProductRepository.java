package com.vit.product_service.repository;

import com.vit.product_service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ProductRepository extends MongoRepository<Product, String> {
    // You can define custom query methods here if needed
}
