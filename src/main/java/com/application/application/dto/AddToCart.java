package com.application.application.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddToCart {
    
    @NotNull
    private long productId;
    @NotNull
    private Integer quantity;

    public AddToCart() {
    }

}