package com.wonderboy.orderservice.controlers;


import com.wonderboy.orderservice.dtos.OrderRequest;
import com.wonderboy.orderservice.models.Order;
import com.wonderboy.orderservice.services.OrderServices;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @AllArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {


    private OrderServices orderServices ;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory",fallbackMethod = "fallbackMethod")
    public  String placeOrder(@RequestBody OrderRequest orderRequest){
        orderServices.placeOrder(orderRequest);
        return "Order Placed Successfully";
    }


    public  String fallbackMethod(OrderRequest orderRequest , RuntimeException runtimeException){
        return  "Oops! Something went wrong, please order after some time" ;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrders(){
        return orderServices.getAll()  ;
    }



}