package com.wonderboy.orderservice.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderLineItemsDto {

    private  String skuCode ;
    private BigDecimal price ;
    private  String quantity ;

}
