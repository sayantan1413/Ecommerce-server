package com.application.application.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    private LocalDateTime createdDate;

    private double totalPrice;

    private String address;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order")
    private List<OrderItems> orderItems;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", updatable = false)
    private User user;

    public Order(Long orderId, User user, double totalPrice) {
        this.orderId = orderId;
        this.user = user;
        this.createdDate = LocalDateTime.now();
        this.totalPrice = totalPrice;
        this.address = user.getAddress();
    }

}
