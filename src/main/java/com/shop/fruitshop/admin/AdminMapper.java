package com.shop.fruitshop.admin;

import com.shop.fruitshop.domain.Admin;
import com.shop.fruitshop.domain.Product;
import com.shop.fruitshop.domain.ProductImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface AdminMapper {

    Admin findById(String id);

    void addProduct(Product product);

    void editProduct(Product product);

    void addProductImage(ProductImage productImage);

    void editProductImage(ProductImage productImage);

    HashMap<String, Object> countStatusAll();

    int countProducts(HashMap<String, Object> param);

    List<HashMap<String, Object>> selectProductAll();

    List<HashMap<String, Object>> selectProductList(HashMap<String, Object> param);

    int saleStopOne(HashMap<String, Object> param);

    int saleStopMany(HashMap<String, Object> param);

    int productDeleteMany(HashMap<String, Object> param);

    int productNameCheck(HashMap<String, String> param);

    Product findProductById(Long id);

    //product id로 이미지 가져오기
    ProductImage findMainImageById(Long id);

    //product id로 내용 이미지리스트 가져오기
    List<ProductImage> findContentImageById(Long id);


}