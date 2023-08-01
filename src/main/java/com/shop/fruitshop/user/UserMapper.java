package com.shop.fruitshop.user;

import com.shop.fruitshop.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

@Mapper
public interface UserMapper {

    User findByEmail(String email);

    void joinUser(User user);

    void joinUserTerm(@Param("userId") Long userId, @Param("termStatus") String termStatus);

    int emailCheck(HashMap<String, String> param);

    int nicknameCheck(HashMap<String, String> param);

    int changePassword(@Param("email") String email, @Param("newPassword") String newPassword);


}