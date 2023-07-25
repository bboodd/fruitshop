package com.shop.fruitshop.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserService implements UserMapper {

    private final UserMapper userMapper;

    @Override
    public HashMap<String, Object> testSelect() {
        return userMapper.testSelect();
    }
}