package com.shop.fruitshop.admin;

import com.shop.fruitshop.domain.Admin;
import com.shop.fruitshop.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService implements AdminMapper {

    private final AdminMapper adminMapper;

    @Override
    public Admin findById(String id) {
        return adminMapper.findById(id);
    }

    @Transactional
    public void addProductAndImage(addProductForm form){
        adminMapper.addProduct(form);
        adminMapper.addProductImage(form);
    }

    @Override
    public void addProduct(addProductForm form){}

    @Override
    public void addProductImage(addProductForm form){}
}
