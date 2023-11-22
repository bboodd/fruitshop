package com.shop.fruitshop.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailDto {

    private long userId;
    private long productId;
    private String name;
    private int price;
    private int discountRate;
    private int stockQuantity;
    private String content;
    private String url;
    private int likeId;
    private int afterDiscount;
}
