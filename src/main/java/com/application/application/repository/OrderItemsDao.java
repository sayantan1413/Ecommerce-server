package com.application.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.application.model.Order;
import com.application.application.model.OrderItems;

@Repository
public interface OrderItemsDao extends JpaRepository<OrderItems, Long> {
    List<OrderItems> findAllByOrder(Order order);
}
