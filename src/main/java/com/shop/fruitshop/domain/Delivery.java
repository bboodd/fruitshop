package com.shop.fruitshop.domain;

import lombok.Data;

@Data
public class Delivery {
    private int id;
    private int userId;
    private String deliveryName;
    private String deliveryUserName;
    private String phoneNumber;
    private int zipcode;
    private String address;
    private DeliveryStatus deliveryStatus;
}
