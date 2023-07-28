package com.shop.fruitshop.domain;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
public class User {
//    CREATE TABLE user (
//            id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
//            email VARCHAR(255) NOT NULL UNIQUE,
//    nickname VARCHAR(255) NOT NULL UNIQUE,
//    password VARCHAR(255) NOT NULL,
//    status TINYINT(1) DEFAULT 1,
//    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
//    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

    private Long id;

    @NotEmpty
    private String email;

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String password;

    private int status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}