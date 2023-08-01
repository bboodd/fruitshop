package com.shop.fruitshop.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Product {
    private Long id;
    private Long categoryId;
    private String name;
    private int price;
    private int status;
    private int stockQuantity;
    private String Content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}