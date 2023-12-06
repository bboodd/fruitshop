package com.shop.fruitshop.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderPageDto {

    private List<OrderPageProductDto> orders;
}
