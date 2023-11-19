package com.wonderboy.orderservice.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "t_orders_line_items")
@Data @AllArgsConstructor @NoArgsConstructor
public class OrderLineItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id ;
    private  String skuCode ;
    private BigDecimal price ;
    private  String quantity ;


}