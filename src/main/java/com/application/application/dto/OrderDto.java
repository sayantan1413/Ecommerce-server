package com.application.application.dto;

import java.time.LocalDateTime;
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

    private LocalDateTime createdDate;

    private List<OrderItemsDto> orderItems;

    private double totalPrice;
}
