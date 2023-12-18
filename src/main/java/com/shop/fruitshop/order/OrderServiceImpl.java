package com.shop.fruitshop.order;

import com.shop.fruitshop.admin.AdminMapper;
import com.shop.fruitshop.domain.OrderStatus;
import com.shop.fruitshop.domain.Product;
import com.shop.fruitshop.domain.User;
import com.shop.fruitshop.exception.NotEnoughStockException;
import com.shop.fruitshop.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdminMapper adminMapper;

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

    @Override
    @Transactional
    public HashMap<String, Object> order(OrderDto orderDto) {
        //데이터 가져오기
        User user = userMapper.findById(orderDto.getUserId());

        List<OrderProductDto> orderProducts = new ArrayList<>();
        for(OrderProductDto opd : orderDto.getOrders()){
            OrderProductDto orderProduct = orderMapper.getOrderProductByProductId(opd.getProductId());

            orderProduct.setAmount(opd.getAmount());

            orderProduct.initSaleTotal();

            orderProducts.add(orderProduct);
        }

        orderDto.setOrders(orderProducts);
        orderDto.setStatus(OrderStatus.ORDER);
        orderDto.getOrderPriceInfo();

        //데이터 삽입
        orderMapper.addOrder(orderDto);
        for(OrderProductDto opd : orderDto.getOrders()){
            opd.setOrderId(orderDto.getId());
            orderMapper.addOrderProduct(opd);
        }

        //재고 차감
        for(OrderProductDto opd : orderDto.getOrders()){
            Product product = adminMapper.findProductById(opd.getProductId());
            product.setStockQuantity(product.getStockQuantity() - opd.getAmount());

            if(product.getStockQuantity() < 0){
                throw new NotEnoughStockException("need more stock");
            }

            orderMapper.reduceStock(product);
        }

        //장바구니 제거
        for(OrderProductDto opd : orderDto.getOrders()){
            HashMap<String, Object> param = new HashMap<>();
            param.put("productId", opd.getProductId());
            param.put("userId", orderDto.getUserId());
            userMapper.deleteCart(param);
        }

        HashMap<String, Object> map = new HashMap<>();

        map.put("price", orderDto.getOrderFinalPrice());
        map.put("id", orderDto.getId());

        return map;

    }
}
