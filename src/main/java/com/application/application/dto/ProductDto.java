package com.application.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductDto {
    private long productId;
    private String productName;
    private String productType;
    private Integer productCount;
    private float productRating;
    private double productPrice;
    private String manufacturerName;
    private String tag;
    private String productImage;
    private String label;
    private String description;
}
