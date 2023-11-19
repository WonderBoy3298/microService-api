package org.wonderboy.productapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.wonderboy.productapp.dtos.ProductRequest;
import org.wonderboy.productapp.dtos.ProductResponse;
import org.wonderboy.productapp.model.Product;
import org.wonderboy.productapp.services.ProductServices;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductServices productServices ;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public  void createProduct(@RequestBody ProductRequest prodRequest){
        productServices.createProduct(prodRequest);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){

        return productServices.getAllProd();

    }






}
