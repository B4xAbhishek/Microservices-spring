package com.vit.inventory_service.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vit.inventory_service.repository.InventoryRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository InventoryRepository;
    
    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode) {
       return InventoryRepository.findBySkuCode(skuCode).isPresent();
    }
}
