package com.application.application.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.application.application.model.Product;
import com.application.application.model.User;

@Repository
@Transactional
public interface ProductDao extends JpaRepository<Product, Long> {

    List<Product> findAllByUser(User user, Pageable paging);

    // // @Query(value = "select * from product where product_name like ?1%",
    // // nativeQuery = true)
    // List<Product> findAllByProductName(String name);

}
