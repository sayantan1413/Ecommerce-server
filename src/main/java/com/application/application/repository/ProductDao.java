package com.application.application.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.application.application.model.Product;
import com.application.application.model.User;

@Repository
@Transactional
public interface ProductDao extends JpaRepository<Product, Long> {

    Page<Product> findAllByUser(User user, Pageable paging);

    List<Product> findByProductNameContainingIgnoreCase(String product_name,
            Pageable paging);

}
