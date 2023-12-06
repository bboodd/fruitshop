package com.shop.fruitshop.user;

import lombok.Data;

import java.util.List;

@Data
public class OrderPageDto {

    private List<OrderPageProductDto> orders;
}
