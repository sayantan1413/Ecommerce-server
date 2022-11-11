package com.application.application.dto;

import javax.validation.constraints.NotNull;

import com.application.application.model.Cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CartItemsDto {
    private @NotNull ProductDto productDto;
    private @NotNull Integer quantity;

    public CartItemsDto(Cart cart) {
        this.setQuantity(cart.getQuantity());
        this.setProductDto(new ProductDto(
                cart.getProduct().getProductId(),
                cart.getProduct().getProductName(),
                cart.getProduct().getProductType(),
                cart.getProduct().getProductCount(),
                cart.getProduct().getProductRating(),
                cart.getProduct().getProductPrice(),
                cart.getProduct().getManufacturerName(),
                cart.getProduct().getTag(),
                cart.getProduct().getProductImage(),
                cart.getProduct().getLabel(),
                cart.getProduct().getDescription()));
    }

}
