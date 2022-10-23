package com.application.application.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class OrderDto {
    private long orderId;

    private String createdDate;

    private List<OrderItemsDto> orderItems;

    private double totalPrice;
}
