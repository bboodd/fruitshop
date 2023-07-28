package com.shop.fruitshop.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserService implements UserMapper {


    private final UserMapper userMapper;

    @Override
    public HashMap<String, Object> testSelect() {
        return userMapper.testSelect();
    }

//    @Override
//    public void joinUser(HashMap<String, Object> requestData) {
//
//        userMapper.joinUser(requestData);
//    }

    @Override
    public void joinUser(UserVo userVo) {

        userMapper.joinUser(userVo);
    }


}