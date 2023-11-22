package com.wonderboy.orderservice.services;


import com.wonderboy.orderservice.configurations.WebClientConfig;
import com.wonderboy.orderservice.dtos.InventoryResponse;
import com.wonderboy.orderservice.dtos.OrderLineItemsDto;
import com.wonderboy.orderservice.dtos.OrderRequest;
import com.wonderboy.orderservice.events.OrderPlacedEvent;
import com.wonderboy.orderservice.models.Order;
import com.wonderboy.orderservice.models.OrderLineItems;
import com.wonderboy.orderservice.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service  @Slf4j @AllArgsConstructor
public class OrderServices {

    private OrderRepository orderRepository;
    private WebClientConfig webClientConfig;


    private  final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate  ;


    public void placeOrder(OrderRequest orderRequest) {

        Order order = new Order();

        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtos().stream().map(orderLineItemsDto -> {
            return mapToDto(orderLineItemsDto);
        }).toList();

        order.setOrderLineItemsList(orderLineItems);
        List<String> skuCodes = order.getOrderLineItemsList()
                .stream()
                .map(item -> item.getSkuCode()).collect(Collectors.toList());

        // call the inventory Service , and place order if items exists
         InventoryResponse[] result  = webClientConfig.webClient().build().get()
                .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("sku-code", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();


        log.info("------!!----------!  {}",result.length);

        Boolean isInStock = Arrays.stream(result).allMatch(InventoryResponse::isInStock);

        log.info("----------------------- {}  ",isInStock);
        if(isInStock){
            kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(order.getOrderNumber())) ;
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


