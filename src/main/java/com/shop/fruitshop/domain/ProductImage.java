package com.shop.fruitshop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {

    private Long id;
    private Long productId;
    private String fileName;
    private String url;
    private String filePath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
