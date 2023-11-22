package com.shop.fruitshop.domain;

import lombok.Data;

@Data
public class Cart {
    private Long id;
    private Long userId;
    private Long productId;
    private Long amount;
}
