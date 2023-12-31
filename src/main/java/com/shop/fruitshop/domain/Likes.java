package com.shop.fruitshop.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Likes {
    private Long id;
    private Long userId;
    private Long productId;
    private LocalDateTime createdAt;
}
