package com.shop.fruitshop.admin;

import com.shop.fruitshop.domain.Admin;
import com.shop.fruitshop.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

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

    @Override
    public HashMap<String, Object> countStatusAll() {
        return adminMapper.countStatusAll();
    }

    @Override
    public int countProducts(HashMap<String, Object> param) {
        return adminMapper.countProducts(param);
    }

    @Override
    public List<HashMap<String, Object>> selectProductAll(){
        return adminMapper.selectProductAll();
    }

    @Override
    public List<HashMap<String, Object>> selectProductList(HashMap<String, Object> param) {
        return adminMapper.selectProductList(param);
    }

    @Override
    public int saleStopOne(HashMap<String, Object> param){
        return adminMapper.saleStopOne(param);
    }

    @Override
    public int saleStopMany(HashMap<String, Object> param){
        return adminMapper.saleStopMany(param);
    }

    @Override
    public int productDeleteMany(HashMap<String, Object> param){
        return adminMapper.productDeleteMany(param);
    }

    @Override
    public int productNameCheck(HashMap<String, String> param){
        return adminMapper.productNameCheck(param);
    }
}
