package com.shop.fruitshop.order;

import java.util.List;

public interface OrderService {

    public List<OrderPageProductDto> getProducts(List<OrderPageProductDto> orders);

}
