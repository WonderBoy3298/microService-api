package com.wonderboy.orderservice.services;


import com.wonderboy.orderservice.configurations.WebClientConfig;
import com.wonderboy.orderservice.dtos.InventoryResponse;
import com.wonderboy.orderservice.dtos.OrderLineItemsDto;
import com.wonderboy.orderservice.dtos.OrderRequest;
import com.wonderboy.orderservice.models.Order;
import com.wonderboy.orderservice.models.OrderLineItems;
import com.wonderboy.orderservice.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service  @Slf4j
public class OrderServices {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private WebClientConfig webClientConfig;


    public void placeOrder(OrderRequest orderRequest) {

        Order order = new Order();

        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtos().stream().map(orderLineItemsDto -> {
            return mapToDto(orderLineItemsDto);
        }).toList();

        order.setOrderLineItemsList(orderLineItems);
        List<String> skuCodes = order.getOrderLineItemsList()
                .stream()
                .map(item -> item.getSkuCode()).toList();

        // call the inventory Service , and place order if items exists

        InventoryResponse[] result  = webClientConfig.webClient().get()
                .uri("http://localhost:8082/api/inventory",uriBuilder -> uriBuilder.queryParam("sku-code",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        log.info("------!!----------!  {}",result.length);

        Boolean isInStock = Arrays.stream(result).allMatch(InventoryResponse::isInStock);

        log.info("----------------------- {}  ",isInStock);
        if(isInStock){
            orderRepository.save(order);
        }else {
            throw new IllegalStateException("Product is not in stock, Please try again later ");
        }


    }

    public  List<Order> getAll(){
        return orderRepository.findAll() ;
    }
    public OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {

        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;

    }

}


