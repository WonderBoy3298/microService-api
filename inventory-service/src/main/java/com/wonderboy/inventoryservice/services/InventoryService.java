package com.wonderboy.inventoryservice.services;



import com.wonderboy.inventoryservice.Repositories.InventoryRepository;
import com.wonderboy.inventoryservice.dtos.InventoryResponse;
import com.wonderboy.inventoryservice.model.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor
public class InventoryService {


    private  final InventoryRepository inventoryRepository ;


    @Transactional(readOnly = true)
    public  List<InventoryResponse> isInStock(List<String> skuCode) {

        return  inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(item -> mapToInventoryResponse(item)).toList();


    }

    public InventoryResponse mapToInventoryResponse(Inventory inventory){
        InventoryResponse inventoryResponse = new InventoryResponse() ;
        inventoryResponse.setInStock(inventory.getQuantity()>0?true:false);
        inventoryResponse.setSkuCode(inventory.getSkuCode());
        return inventoryResponse ;
    }


}
