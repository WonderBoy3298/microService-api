package com.wonderboy.inventoryservice;

import com.wonderboy.inventoryservice.Repositories.InventoryRepository;
import com.wonderboy.inventoryservice.model.Inventory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}


	@Bean
	CommandLineRunner commandLineRunner(InventoryRepository inventoryRepository ){
		return args -> {

			Inventory inventory = new Inventory();
			inventory.setQuantity(13);
			inventory.setSkuCode("iphone_13_mini");

			Inventory inventory4 = new Inventory();
			inventory4.setQuantity(0);
			inventory4.setSkuCode("iphone_13_pro");

			Inventory inventory3 = new Inventory();
			inventory3.setQuantity(2);
			inventory3.setSkuCode("iphone_13");

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory4);
			inventoryRepository.save(inventory3) ;

		};
	}


}

