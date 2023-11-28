package com.shop.fruitshop.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

    private Long cartId;
    private Long userId;
    private Long productId;
    private int amount;
    private String name;
    private int price;
    private int discountRate;
    private int stockQuantity;
    private String url;
    private int total;
    private int totalPrice;
    private int totalDiscount;
}
