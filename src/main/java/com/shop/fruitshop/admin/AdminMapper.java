package com.shop.fruitshop.admin;

import com.shop.fruitshop.domain.Admin;
import com.shop.fruitshop.domain.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {

    Admin findById(String id);

    void addProduct(addProductForm form);

}