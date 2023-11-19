package com.wonderboy.inventoryservice.Repositories;

import com.wonderboy.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository  extends JpaRepository<Inventory,Long> {




    List<Inventory> findBySkuCodeIn(List<String> skucode);

}
