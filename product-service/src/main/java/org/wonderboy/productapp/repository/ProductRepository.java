package org.wonderboy.productapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.wonderboy.productapp.model.Product;

public interface ProductRepository extends MongoRepository<Product , String> {
}
