package com.shoppingapplication.inventoryservice.service;

import com.shoppingapplication.inventoryservice.dto.InventoryResponse;
import com.shoppingapplication.inventoryservice.model.Inventory;
import com.shoppingapplication.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {


    private final InventoryRepository inventoryRepository;
    @Transactional(readOnly =true)
    public List<InventoryResponse> isInStock(List<String> skuCode){

        boolean present = false;


        return inventoryRepository.findBySkuCodeIn(skuCode).stream().map(x->

            InventoryResponse.builder().skuCode(x.getSkuCode()).isInStock(x.getQuantity()>0).build()
        ).toList();


    }

}
