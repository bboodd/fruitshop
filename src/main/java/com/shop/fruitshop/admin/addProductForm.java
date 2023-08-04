package com.shop.fruitshop.admin;

import com.shop.fruitshop.domain.ProductImage;
import lombok.Data;

@Data
public class addProductForm {

    private String name;
    private String categoryName;
    private int price;
    private int discountRate = 0;
    private int stockQuantity;
//    private ProductImage productImage;
    private String content;
}
