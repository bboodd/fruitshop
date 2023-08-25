package com.shop.fruitshop.domain;

import lombok.Data;

@Data
public class Likes {
    private Long id;
    private Long userId;
    private Long productId;
}
