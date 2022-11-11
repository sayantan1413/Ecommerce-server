package com.application.application.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(CommentId.class)
public class Comments {

    // @GeneratedValue(strategy = GenerationType.AUTO)
    // private long commentId;
    @Id
    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", updatable = false)
    private User user;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "productId", updatable = false)
    private Product product;

    private String message;

    private LocalDateTime createdDate;

    public Comments(User user, Product product, String message) {
        this.user = user;
        this.product = product;
        this.message = message;
        this.createdDate = LocalDateTime.now();
    }

}
