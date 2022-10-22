package com.application.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class OrderItemsDto {
    private long orderItemId;

    private ProductDto productDto;

    private int quantity;
}
