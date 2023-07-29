package com.shop.fruitshop.user;

import com.shop.fruitshop.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
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

//    @Override
//    public void joinUser(UserVo userVo) {
//
//        userMapper.joinUser(userVo);
//    }

    @Override
    public User login(UserLoginForm form){
        System.out.println("---------------------------");
        System.out.println("form = " + form);
        System.out.println("---------------------------");
        return userMapper.login(form);
    }

    @Transactional
    public String join(User user, List<String> termStatus){
        //유저 조인
        userMapper.joinUser(user);
        System.out.println("-------------------------------");
        System.out.println(user.getId());
        System.out.println("-----------------------------");

        //선택 약관 추가
        if (termStatus != null){
            termStatus.forEach(term -> userMapper.joinUserTerm(user.getId(), term));
        }

        return user.getEmail();
    }

    @Override
    public void joinUser(User user){

    }
    @Override
    public void joinUserTerm(Long userId, String termStatus){
    }

    @Override
    public int emailCheck(HashMap<String, String> param){
        return userMapper.emailCheck(param);
    }


}