package com.wonderboy.inventoryservice.controller;

import com.wonderboy.inventoryservice.dtos.InventoryResponse;
import com.wonderboy.inventoryservice.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private  final InventoryService inventoryService ;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public  List<InventoryResponse> isInStock(@RequestParam("sku-code") List<String> skuCode){
        return  inventoryService.isInStock(skuCode) ;
    }



    @GetMapping("/test")
    public String test(){
        return "Service is working fine ";
    }


}
