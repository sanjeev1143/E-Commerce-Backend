package com.shoppingapplication.orderservice.repository;

import com.shoppingapplication.orderservice.dto.OrderRequest;
import com.shoppingapplication.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
