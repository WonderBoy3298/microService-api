package com.wonderboy.orderservice.repositories;


import com.wonderboy.orderservice.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
