package com.shop.fruitshop.order;

import com.shop.fruitshop.domain.Product;
import com.shop.fruitshop.user.CartDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    OrderPageProductDto getProductByProductId(Long productId);

    //주문 상품 정보
    OrderProductDto getOrderProductByProductId(Long productId);

    //주문 테이블 등록
    int addOrder(OrderDto orderDto);

    //주문 상품 테이블 등록
    int addOrderProduct(OrderProductDto orderProductDto);

    //상품 재고 차감
    int reduceStock(Product product);

}
