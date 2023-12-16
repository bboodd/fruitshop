package com.shop.fruitshop.order;

import java.util.HashMap;
import java.util.List;

public interface OrderService {

    public List<OrderPageProductDto> getProducts(List<OrderPageProductDto> orders);

    public HashMap<String, Object> getTotal(List<OrderPageProductDto> orders);

    public void order(OrderDto orderDto);

}
