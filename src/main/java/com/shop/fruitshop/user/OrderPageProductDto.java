package com.shop.fruitshop.user;

import lombok.Data;

@Data
public class OrderPageProductDto {

    //view 에서 받아올 값
    private Long productId;
    private int amount;

    //db에서 꺼내올 값
    private String name;
    private int price;
    private int discountRate;
    private String url;

    //만들어 낼 값
    private int totalDiscount;
    private int totalPrice;

    public void initTotal(){
        this.totalDiscount = (int) this.price * (this.discountRate / 100) * this.amount;
        this.totalPrice = (int) this.price * (1 - (this.discountRate / 100)) * this.amount;
    }
}
