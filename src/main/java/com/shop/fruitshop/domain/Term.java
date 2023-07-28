package com.shop.fruitshop.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Term {
//                      id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
//                      title VARCHAR(255) NOT NULL,
//                      content TEXT NOT NULL,
//                      required TINYINT(1),
//                      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
//                      updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

    private Long id;
    private String title;
    private String content;
    private Boolean required;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
