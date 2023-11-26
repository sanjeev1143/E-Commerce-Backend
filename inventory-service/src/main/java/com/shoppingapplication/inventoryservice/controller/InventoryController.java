package com.shoppingapplication.inventoryservice.controller;

import com.shoppingapplication.inventoryservice.dto.InventoryResponse;
import com.shoppingapplication.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCod){

        return inventoryService.isInStock(skuCod);
    }

}
