package com.shop.fruitshop.order;

import com.shop.fruitshop.domain.OrderStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDto {

    private Long id;
    private Long userId;
    private String deliveryUserName;
    private String phoneNumber;
    private int zipcode;
    private String address;
    private String request;
    private OrderStatus status;
    private String addressDetail;
    private String payment;
    private Date orderAt;

    //주문 상품
    private List<OrderProductDto> orders;

    //db에 존재하지 않는 데이터

    private int orderPrice;
    private int orderDiscount;

    //최종 판매 비용
    private int orderFinalPrice;

    public void getOrderPriceInfo(){
        for(OrderProductDto order : orders){
            orderPrice += order.getTotalPrice();
            orderDiscount += order.getTotalDiscount();
        }

        if(orderPrice - orderDiscount >= 50000){
            orderFinalPrice = orderPrice - orderDiscount;
        } else{
            orderFinalPrice = orderPrice - orderDiscount + 3000;
        }
    }

}
