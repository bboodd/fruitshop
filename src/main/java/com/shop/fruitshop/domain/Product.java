package com.shop.fruitshop.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Product {
    private Long id;
    private Long categoryId;
    private String name;
    private int price;
    private int discountRate;
    private int status;
    private int stockQuantity;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ProductImage> images;
}