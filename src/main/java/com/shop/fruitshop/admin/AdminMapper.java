package com.shop.fruitshop.admin;

import com.shop.fruitshop.domain.Admin;
import com.shop.fruitshop.domain.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface AdminMapper {

    Admin findById(String id);

    void addProduct(addProductForm form);

    void addProductImage(addProductForm form);

    HashMap<String, Object> countStatusAll();

    int countProducts(HashMap<String, Object> param);

    List<HashMap<String, Object>> selectProductAll();

    List<HashMap<String, Object>> selectProductList(HashMap<String, Object> param);

    int saleStopOne(HashMap<String, Object> param);

    int saleStopMany(HashMap<String, Object> param);

    int productDeleteMany(HashMap<String, Object> param);

    int productNameCheck(HashMap<String, String> param);


}