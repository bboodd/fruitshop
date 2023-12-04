package com.shop.fruitshop.domain;

import lombok.Data;

@Data
public class OrderProduct {
    private int id;
    private int orderId;
    private int productId;
    private int price;
    private int amount;
    private int discountRate;
    private String url;
}
