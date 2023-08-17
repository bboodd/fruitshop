package com.shop.fruitshop.admin;

import com.shop.fruitshop.domain.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductForm {

    private Long id;
    private Long categoryId;
    private String name;
    private int price;
    private int discountRate;
    private int stockQuantity;
    private String content;

    private List<ProductImage> images;


    private String url;
}
