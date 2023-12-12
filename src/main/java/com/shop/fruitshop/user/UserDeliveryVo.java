package com.shop.fruitshop.user;

import com.shop.fruitshop.domain.DeliveryStatus;
import lombok.Data;

@Data
public class UserDeliveryVo {

    private Long deliveryId;
    private Long userId;
    private String deliveryName;
    private String deliveryUserName;
    private String phoneNumber;
    private int zipcode;
    private String address;
    private String addressDetail;
}
