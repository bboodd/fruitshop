package com.shop.fruitshop.order;

import com.shop.fruitshop.domain.OrderStatus;
import com.shop.fruitshop.domain.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;

//    상품 정보(주문 처리)
    @Test
    void getOrderProductByProductId() {
        OrderProductDto orderProduct = orderMapper.getOrderProductByProductId(42L);

        System.out.println("result : " + orderProduct);
    }

//    주문 테이블 등록
    @Test
    void addOrder() {

        OrderDto orderDto = new OrderDto();
        List<OrderProductDto> orders = new ArrayList<>();

        OrderProductDto order1 = new OrderProductDto();

        order1.setProductId(42L);
        order1.setAmount(5);
        order1.setPrice(1000000);
        order1.setDiscountRate(10);
        order1.initSaleTotal();

        orders.add(order1);

        orderDto.setOrders(orders);

        orderDto.setAddress("test");
        orderDto.setAddressDetail("testTest");
        orderDto.setPhoneNumber("01012345678");
        orderDto.setUserId(1L);
        orderDto.setZipcode(123456);
        orderDto.setDeliveryUserName("test");
        orderDto.setRequest("test");
        orderDto.setPayment("test");
        orderDto.setStatus(OrderStatus.ORDER);
        orderDto.getOrderPriceInfo();

        orderMapper.addOrder(orderDto);
    }

//    주문 상품 테이블 등록
    @Test
    void addOrderProduct() {

        OrderProductDto orderProductDto = new OrderProductDto();

        orderProductDto.setProductId(42L);
        orderProductDto.setAmount(5);
        orderProductDto.setOrderId(1L);
        orderProductDto.setPrice(1000000);
        orderProductDto.setDiscountRate(10);

        orderProductDto.initSaleTotal();

        orderMapper.addOrderProduct(orderProductDto);
    }

//    상품 재고 변경
    @Test
    void reduceStock() {

        Product product = new Product();

        product.setId(42L);
        product.setStockQuantity(95);

        orderMapper.reduceStock(product);
    }
}