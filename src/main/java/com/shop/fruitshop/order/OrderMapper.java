package com.shop.fruitshop.order;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    OrderPageProductDto getProductByProductId(Long productId);
}
