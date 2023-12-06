package com.shop.fruitshop.order;

import com.shop.fruitshop.domain.User;
import com.shop.fruitshop.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    private final UserService userService;


    @GetMapping("/order/{userId}")
    public String orderPageGet(@PathVariable("userId")long userId, OrderPageDto opd, Model model,
                             @SessionAttribute(name = "loginUser", required = false) User loginUser){

        if(loginUser == null){
            return "/user/login";
        }

        model.addAttribute("orderList", orderService.getProducts(opd.getOrders()));
        model.addAttribute("delivery", userService.getUserDeliveryByUserId(userId));

        return "/order";
    }
}
