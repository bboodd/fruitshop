package com.shop.fruitshop.domain;

import lombok.Data;

@Data
public class Order {
    private int id;
    private int userId;
    private int orderNumber;
    private String deliveryUserName;
    private String phoneNumber;
    private int zipcode;
    private String address;
    private String request;
    private OrderStatus orderStatus;
    private String payment;
}
