package com.shop.fruitshop.user;

import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.HashMap;

@Mapper
public interface UserMapper {

    HashMap<String, Object> testSelect();

//    void joinUser(HashMap<String, Object> requestData);
        void joinUser(UserVo userVo);
}