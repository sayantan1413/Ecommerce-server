package com.application.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.application.model.Comments;
import com.application.application.model.Product;
import com.application.application.model.User;

@Repository
public interface CommentDao extends JpaRepository<Comments, User> {

    List<Comments> findByUser(User user);

    List<Comments> findByProduct(Product product);

}
