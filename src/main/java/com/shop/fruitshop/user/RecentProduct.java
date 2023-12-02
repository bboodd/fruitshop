package com.shop.fruitshop.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecentProduct {
    private String imageUrl;
    private Long productId;

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        RecentProduct recentProduct = (RecentProduct) o;
        return Objects.equals(imageUrl, recentProduct.imageUrl);
    }

    @Override
    public int hashCode(){
        return Objects.hash(imageUrl);
    }
}
