package com.shop.fruitshop.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
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

    @Override
    public HashMap<String, Object> getTotal(List<OrderPageProductDto> orders){
        int totalPrice = 0;
        int totalDiscount = 0;
        int delivery = 0;
        int finalTotalPrice = 0;
        HashMap<String, Object> map = new HashMap<>();

        for(OrderPageProductDto opd : orders){
            totalPrice += opd.getTotalPrice();
            totalDiscount += opd.getTotalDiscount();
        }

        if(totalPrice - totalDiscount >= 50000){
            finalTotalPrice = totalPrice - totalDiscount;
        } else{
            delivery = 3000;
            finalTotalPrice = totalPrice - totalDiscount + delivery;
        }

        map.put("totalPrice", totalPrice);
        map.put("totalDiscount", totalDiscount);
        map.put("delivery", delivery);
        map.put("finalTotalPrice", finalTotalPrice);

        return map;

    }
}
