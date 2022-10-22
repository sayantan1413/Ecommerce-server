package com.application.application.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.application.model.Order;
import com.application.application.model.User;

@Repository
@Transactional
public interface OrderDao extends JpaRepository<Order, Long> {
    List<Order> findAllByUserOrderByCreatedDateDesc(User user);
}
