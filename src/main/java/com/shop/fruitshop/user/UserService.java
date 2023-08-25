package com.shop.fruitshop.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.fruitshop.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserMapper {


    private final UserMapper userMapper;


    @Override
    public User findByEmail(String email){
        return userMapper.findByEmail(email);
    }

    @Transactional
    public String join(User user, List<String> termStatus){
        //유저 조인
        userMapper.joinUser(user);

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

    @Override
    public int nicknameCheck(HashMap<String, String> param){
        return userMapper.nicknameCheck(param);
    }

    @Override
    public int changePassword(String email, String newPassword){ return userMapper.changePassword(email, newPassword);}

    @Override
    public List<HashMap<String, Object>> selectProductAndUrlAll(){
        return userMapper.selectProductAndUrlAll();
    }

    public PageInfo<HashMap<String, Object>> selectProductAndUrlAllWithPaging(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<HashMap<String, Object>> list = new PageInfo<>(userMapper.selectProductAndUrlAll());
        return list;
    }

    @Override
    public List<HashMap<String, Object>> selectProductAndUrlList(HashMap<String, Object> param) {
        return userMapper.selectProductAndUrlList(param);
    }

    public PageInfo<HashMap<String, Object>> selectProductAndUrlListWithPaging(HashMap<String, Object> param){
        PageHelper.startPage((Integer) param.get("pageNum"), (Integer) param.get("pageSize"));
        PageInfo<HashMap<String, Object>> list = new PageInfo<>(userMapper.selectProductAndUrlList(param));
        return list;
    }

    @Override
    public List<HashMap<String, Object>> selectProductAndUrlAndLikeAll(Long id){
        return userMapper.selectProductAndUrlAndLikeAll(id);
    }

    public PageInfo<HashMap<String, Object>> selectProductAndUrlAndLikeAllWithPaging(Long id, int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<HashMap<String, Object>> list = new PageInfo<>(userMapper.selectProductAndUrlAndLikeAll(id));
        return list;
    }

    @Override
    public void addLike(HashMap<String, Object> param) {
        userMapper.addLike(param);
    }

    @Override
    public void deleteLike(HashMap<String, Object> param) {
        userMapper.deleteLike(param);
    }
}