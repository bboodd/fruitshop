package com.shop.fruitshop.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Term {

    private Long id;
    private String title;
    private String content;
    private Boolean required;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
