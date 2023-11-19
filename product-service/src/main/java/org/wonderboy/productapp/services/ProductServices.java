package org.wonderboy.productapp.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wonderboy.productapp.dtos.ProductRequest;
import org.wonderboy.productapp.dtos.ProductResponse;
import org.wonderboy.productapp.model.Product;
import org.wonderboy.productapp.repository.ProductRepository;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j
public class ProductServices {

    @Autowired
    private ProductRepository productRepository ;


    public  void createProduct(ProductRequest productRequest){

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build() ;

            productRepository.save(product);

        log.info("Product {} is Saved",product.getId());

    }

    public List<ProductResponse> getAllProd(){

        List<Product> productList = productRepository.findAll();
        return  productList.stream().map(product -> productToProductResponse(product)).toList();

    }


    private ProductResponse productToProductResponse(Product product){

        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setDescription(product.getDescription());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());

        return productResponse ;
    }


}
