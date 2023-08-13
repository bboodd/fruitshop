package com.shop.fruitshop.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductImage {

    private Long id;
    private Long productId;
    private String fileName;
    private String url;
    private String filePath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
