package com.shop.fruitshop.user;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface UserMapper {

    HashMap<String, Object> testSelect();
}