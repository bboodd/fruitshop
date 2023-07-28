package com.shop.fruitshop.domain;

import lombok.Data;

@Data
public class UserTerm {
//                           id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
//                           user_id INT UNSIGNED NOT NULL,
//                           term_id INT UNSIGNED NOT NULL,
//                           FOREIGN KEY (user_id) REFERENCES user(id),
//                           FOREIGN KEY (term_id) REFERENCES term(id)

    private Long id;
    private Long userId;
    private Long termId;
}
