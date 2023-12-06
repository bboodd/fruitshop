package com.shop.fruitshop.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService{

    private final OrderMapper orderMapper;

    @Override
    public List<OrderPageProductDto> getProducts(List<OrderPageProductDto> orders){
        List<OrderPageProductDto> result = new ArrayList<>();

        for(OrderPageProductDto opd : orders){

            OrderPageProductDto product = orderMapper.getProductByProductId(opd.getProductId());

            product.setAmount(opd.getAmount());

            product.initTotal();

            result.add(product);
        }

        return result;
    }
}
