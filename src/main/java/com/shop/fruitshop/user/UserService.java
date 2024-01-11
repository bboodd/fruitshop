package com.shop.fruitshop.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.fruitshop.domain.Product;
import com.shop.fruitshop.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserMapper {

    private ObjectMapper objectMapper = new ObjectMapper();
    public static final String RECENT_PRODUCTS = "recentProducts";
    private static final int MAX_RECENT_PRODUCTS_SIZE = 3;


    private final UserMapper userMapper;


    @Override
    public User findByEmail(String email){
        return userMapper.findByEmail(email);
    }

    @Override
    public User findById(Long id){
        return userMapper.findById(id);
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

    @Override
    public int countUserLike(HashMap<String, Object> param){
        return userMapper.countUserLike(param);
    }

    @Override
    public int countUserCart(HashMap<String, Object> param){
        return userMapper.countUserCart(param);
    }

    @Override
    public void addCart(HashMap<String, Object> param){
        userMapper.addCart(param);
    }

    @Override
    public void deleteCart(HashMap<String, Object> param){
        userMapper.deleteCart(param);
    }

    @Override
    public void updateCart(HashMap<String, Object> param){
        userMapper.updateCart(param);
    }

    @Override
    public int countCartAmount(HashMap<String, Object> param){
        return userMapper.countCartAmount(param);
    }

    @Override
    public int checkCart(HashMap<String, Object> param){
        return userMapper.checkCart(param);
    }

    @Override
    public DetailDto findProductDetail(HashMap<String, Object> param){
        return userMapper.findProductDetail(param);
    }

    @Override
    public int getMaxAmount(HashMap<String, Object> param){
        return userMapper.getMaxAmount(param);
    }

    @Override
    public List<CartDto> findCartByUserId(Long userId){
        return userMapper.findCartByUserId(userId);
    }

    @Override
    public TotalDto findCartTotalPrice(Long userId){
        return userMapper.findCartTotalPrice(userId);
    }

    public void createRecentlyProducts(Long productId, HttpServletResponse response, HttpServletRequest request, String imageUrl) throws IOException {
        List<RecentProduct> recentProducts = getRecentProductsByCookie(request);

        if(!recentProducts.contains(new RecentProduct(imageUrl, productId))){
            recentProducts.add(0, new RecentProduct(imageUrl, productId));

            if(recentProducts.size() > MAX_RECENT_PRODUCTS_SIZE){
                recentProducts.remove(recentProducts.size() - 1);
            }
        } else{
            Iterator<RecentProduct> iterator = recentProducts.iterator();
            while(iterator.hasNext()){
                RecentProduct recentProduct = iterator.next();

                if(recentProduct.equals(new RecentProduct(imageUrl, productId))){
                    recentProducts.remove(recentProduct);
                    break;
                }
            }
            recentProducts.add(0, new RecentProduct(imageUrl, productId));
        }

        String recentProductsJson = objectMapper.writeValueAsString(recentProducts);
        String encodedRecentProductsJson = URLEncoder.encode(recentProductsJson, StandardCharsets.UTF_8.toString());

        Cookie cookie = new Cookie(RECENT_PRODUCTS, encodedRecentProductsJson);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);
    }

    public List<RecentProduct> getRecentProductsByCookie(HttpServletRequest request) throws IOException {

        Cookie[] cookies = request.getCookies();
        String cookieValue = getCookieValue(cookies, RECENT_PRODUCTS);

        if(cookieValue != null){
            String decodedCookieValue = URLDecoder.decode(cookieValue, StandardCharsets.UTF_8.toString());
            return objectMapper.readValue(decodedCookieValue, new TypeReference<List<RecentProduct>>() {});
        }

        return new ArrayList<>();
    }

    private String getCookieValue(Cookie[] cookies, String cookieName){
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(cookieName)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public List<UserDeliveryVo> getUserDeliveryByUserId(Long userId){
        return userMapper.getUserDeliveryByUserId(userId);
    }

    @Override
    public HashMap<String, Object> getDeliveryByDeliveryId(HashMap<String, Object> param) {
        return userMapper.getDeliveryByDeliveryId(param);
    }

    @Override
    public int addDelivery(HashMap<String, Object> param) {
        userMapper.addDelivery(param);
        int id = Integer.parseInt(String.valueOf(param.get("id")));
        return id;
    }

    public void aside(Model model, Long userId, String userName,HttpServletRequest request) throws IOException{
        //aside
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        model.addAttribute("userId", userId);
        model.addAttribute("likeCount", countUserLike(map));
        model.addAttribute("cartCount", countUserCart(map));
        model.addAttribute("userName", userName);

        //최근 본 상품 목록 보여주기
        model.addAttribute("recentProducts", getRecentProductsByCookie(request));
    }

    @Override
    public List<HashMap<String, Object>> selectUserOrders(Long userId){
        return userMapper.selectUserOrders(userId);
    }
}