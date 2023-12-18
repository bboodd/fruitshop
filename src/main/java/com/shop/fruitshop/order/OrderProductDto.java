package com.shop.fruitshop.order;

import lombok.Data;

@Data
public class OrderProductDto {

    private Long id;
    private Long productId;
    private Long orderId;
    private int price;
    private int amount;
    private int discountRate;

    //db에 존재하지 않는 데이터
    private int totalPrice;
    private int totalDiscount;
    private int totalFinalPrice;

    public void initSaleTotal(){
        this.totalPrice = this.price * amount;
        this.totalDiscount = (int)((this.price * (double)(this.discountRate / 100)) * this.amount);
        this.totalFinalPrice = this.totalPrice - this.totalDiscount;
    }
}
