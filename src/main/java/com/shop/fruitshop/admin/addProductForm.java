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
    private String content;
//이미지
    private String originName;
    private String fileName;
    private String fileSize;
}
