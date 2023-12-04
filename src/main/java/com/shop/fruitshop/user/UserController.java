package com.shop.fruitshop.user;

import com.github.pagehelper.PageInfo;
import com.shop.fruitshop.admin.ProductForm;
import com.shop.fruitshop.domain.Product;
import com.shop.fruitshop.domain.ProductImage;
import com.shop.fruitshop.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {


    private final UserService userService;
    private final PasswordEncoder pwEncoder;


    @GetMapping("/")
    public String index(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                        @RequestParam(required = false, defaultValue = "1") int pageNum,
                        @RequestParam(required = false, defaultValue = "9") int pageSize,
                        Model model,
                        HttpServletRequest request) throws IOException {

        //최근 본 상품목록

        model.addAttribute("recentProducts", userService.getRecentProductsByCookie(request));

        if (loginUser == null) {
            model.addAttribute("list", userService.selectProductAndUrlAllWithPaging(pageNum, pageSize));
            return "index";
        }

        HashMap<String,Object> data_count = new HashMap<>();
        data_count.put("userId", loginUser.getId());

        model.addAttribute("likeCount", userService.countUserLike(data_count));
        model.addAttribute("cartCount", userService.countUserCart(data_count));
        model.addAttribute("user", loginUser);
        model.addAttribute("list", userService.selectProductAndUrlAndLikeAllWithPaging(loginUser.getId(), pageNum, pageSize));
        return "loginIndex";
    }

    @ResponseBody
    @PostMapping("/product")
    public HashMap<String, Object> product(@RequestBody HashMap<String, Object> param){

        PageInfo<HashMap<String, Object>> data = userService.selectProductAndUrlListWithPaging(param);

        HashMap<String,Object> data_count = new HashMap<>();

        data_count.put("data",data);

        return data_count;
    }

    @ResponseBody
    @PostMapping("/addLike")
    public int addLike(@RequestBody HashMap<String, Object> param){
        userService.addLike(param);
        return userService.countUserLike(param);
    }

    @ResponseBody
    @PostMapping("/deleteLike")
    public int deleteLike(@RequestBody HashMap<String, Object> param){
        userService.deleteLike(param);
        return userService.countUserLike(param);
    }

    @GetMapping("user/{pathName}")
    public String path(@PathVariable String pathName) {
        return "user/"+pathName;
    }

    @GetMapping("user/favicon.ico")
    @ResponseBody
    void noFavicon() {
    }

    //회원가입
    @PostMapping("/user/join")
    public String join(@Valid User user,
                       BindingResult bindingResult,
                       @RequestParam(required = false) List<String> termStatus,
                       RedirectAttributes re) {

        if (bindingResult.hasErrors()) {
            return "user/join";
        }

        String rawPw = "";
        String encodePw = "";

        rawPw = user.getPassword();
        encodePw = pwEncoder.encode(rawPw);
        user.setPassword(encodePw);

        String joinEmail = userService.join(user, termStatus);

        re.addFlashAttribute("email", joinEmail);

        return "redirect:/user/joinConfirm?email={email}";
    }

    //로그인
    @PostMapping("/user/login")
    public String login(@Valid UserLoginForm form,
                        BindingResult bindingResult,
                        Model model,
                        HttpServletRequest request){

        if (bindingResult.hasErrors()){
            return "user/login";
        }

        HttpSession session = request.getSession();

        String rawPw = "";
        String encodePw = "";

        User loginUser = userService.findByEmail(form.getEmail());

        if (loginUser != null) {
            rawPw = form.getPassword();
            encodePw = loginUser.getPassword();

            if (true == pwEncoder.matches(rawPw, encodePw)) {   //비밀번호 일치 여부 판단
                loginUser.setPassword("");    // 인코딩된 비밀번호 정보 지움
                session.setAttribute("loginUser", loginUser);    // session에 사용자 정보 저장
                return "redirect:/";
            } else {
                model.addAttribute("loginCheck", "비밀번호가 일치하지 않습니다.");
                return "user/login";
            }
        } else {
            model.addAttribute("loginCheck", "아이디가 일치하지 않습니다.");
            return "user/login";
        }
    }

    //로그아웃
    @RequestMapping("/user/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    //이메일 중복 체크
    @RequestMapping("user/emailCheck")
    @ResponseBody
    public int emailCheck(@RequestBody HashMap<String, String> param){

        return userService.emailCheck(param);
    }

    //닉네임 중복 체크
    @RequestMapping("user/nicknameCheck")
    @ResponseBody
    public int nicknameCheck(@RequestBody HashMap<String, String> param){

        return userService.nicknameCheck(param);
    }

    @RequestMapping("/user/changePassword")
    @ResponseBody
    public int changePassword(@RequestBody HashMap<String, String> param){

        String rawPw = param.get("newPassword");
        String encodePw = pwEncoder.encode(rawPw);

        return userService.changePassword(param.get("email"), encodePw);
    }

    @ResponseBody
    @PostMapping("/addCart")
    public int addCart(@RequestBody HashMap<String, Object> param){

        if(userService.checkCart(param) != 0){
            int addAmount = (int)param.get("amount");
            int defaultAmount = userService.countCartAmount(param);
            param.put("amount", addAmount+defaultAmount);
            userService.updateCart(param);
            return userService.countUserCart(param);
        }

        userService.addCart(param);

        return userService.countUserCart(param);
    }

    @ResponseBody
    @PostMapping("/deleteCart")
    public int deleteCart(@RequestBody HashMap<String, Object> param){
        userService.deleteCart(param);
        return userService.countUserCart(param);
    }

    @ResponseBody
    @PostMapping("/updateCart")
    public void updateCart(@RequestBody HashMap<String, Object> param){

        int maxAmount = userService.getMaxAmount(param);
        int addAmount = (int) param.get("amount");

        if(addAmount <= maxAmount ){
            userService.updateCart(param);
        }
    }

    @GetMapping("user/product/{productId}/detail")
    public String detail(@PathVariable long productId,
                         @SessionAttribute(name = "loginUser", required = false) User loginUser,
                         Model model,
                         HttpServletResponse response,
                         HttpServletRequest request) throws IOException {

        HashMap<String, Object> param = new HashMap<>();
        param.put("productId", productId);
        if(loginUser != null){
            param.put("userId", loginUser.getId());

            if(userService.checkCart(param) != 0){
                model.addAttribute("cartAmount", userService.countCartAmount(param));
            }
        }

        DetailDto detail = userService.findProductDetail(param);

        int afterDiscount =(int) (detail.getPrice() * ((double)1 - ((double)detail.getDiscountRate() / 100)));
        detail.setAfterDiscount(afterDiscount);

        if(loginUser != null){
            HashMap<String, Object> userId = new HashMap<>();
            userId.put("userId", loginUser.getId());
            model.addAttribute("userId", loginUser.getId());
            model.addAttribute("likeCount", userService.countUserLike(userId));
            model.addAttribute("cartCount", userService.countUserCart(userId));
            model.addAttribute("userName", loginUser.getNickname());
        }
        model.addAttribute("detail", detail);

        //최근 본 상품 목록 쿠키 생성
        String imageUrl = detail.getUrl();
        userService.createRecentlyProducts(productId, response, request, imageUrl);

        //최근 본 상품 목록 보여주기
        model.addAttribute("recentProducts", userService.getRecentProductsByCookie(request));

        return "user/detail";
    }

    @GetMapping("user/cart")
    public String cart(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                       Model model,
                       HttpServletRequest request) throws IOException{
        if(loginUser == null){
            return "user/login";
        }

        HashMap<String, Object> param = new HashMap<>();
        param.put("userId", loginUser.getId());

        model.addAttribute("likeCount", userService.countUserLike(param));
        model.addAttribute("cartCount", userService.countUserCart(param));
        model.addAttribute("user", loginUser);
        //최근 본 상품 목록 보여주기
        model.addAttribute("recentProducts", userService.getRecentProductsByCookie(request));

        model.addAttribute("userId", loginUser.getId());
        model.addAttribute("count", userService.countUserCart(param));
        model.addAttribute("cartList", userService.findCartByUserId(loginUser.getId()) !=
                null ? userService.findCartByUserId(loginUser.getId()) : new CartDto());
        model.addAttribute("totalSum", userService.findCartTotalPrice(loginUser.getId()) !=
                null ? userService.findCartTotalPrice(loginUser.getId()) : new TotalDto());


        return "user/cart";
    }

}