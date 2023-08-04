package com.shop.fruitshop.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductImage {

    private Long id;
    private Long productId;
    private String originName;
    private String fileName;
    private int fileSize;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
