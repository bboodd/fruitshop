package com.shop.fruitshop.order;

import com.shop.fruitshop.domain.User;
import com.shop.fruitshop.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    private final UserService userService;


    @GetMapping("/order/{userId}")
    public String orderPageGet(@PathVariable("userId")long userId, OrderPageDto opd, Model model,
                               @SessionAttribute(name = "loginUser", required = false) User loginUser,
                               HttpServletRequest request) throws IOException {

        if(loginUser == null){
            return "/user/login";
        }

        //aside
        userService.aside(model, loginUser.getId(), loginUser.getNickname(), request);

        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        model.addAttribute("likeCount", userService.countUserLike(map));
        model.addAttribute("cartCount", userService.countUserCart(map));

        List<OrderPageProductDto> orders = orderService.getProducts(opd.getOrders());

        model.addAttribute("orderList", orderService.getProducts(orders));
        model.addAttribute("total", orderService.getTotal(orders));
        model.addAttribute("deliveryList", userService.getUserDeliveryByUserId(userId));

        return "order";
    }

    @PostMapping("/order")
    public String orderPagePost(OrderDto od, RedirectAttributes re, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "order";
        }

        System.out.println(od);

        HashMap<String, Object> map = orderService.order(od);

        re.addFlashAttribute("info", map);
//        re.addAttribute("price", orderFinalPrice);

        return "redirect:/user/orderSuccess";
    }

}
