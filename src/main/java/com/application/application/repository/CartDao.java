package com.application.application.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.application.model.Cart;
import com.application.application.model.Product;
import com.application.application.model.User;

@Repository
public interface CartDao extends JpaRepository<Cart, Integer> {

    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);

    @Transactional
    void deleteByUser(User user);

    Optional<Cart> findByUserAndProduct(User user, Product product);

    @Transactional
    void deleteByProductAndUser(Product product, User user);

    List<Cart> findAllByUser(User user);
}
