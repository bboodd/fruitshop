package com.shop.fruitshop.order;

import com.shop.fruitshop.domain.User;
import com.shop.fruitshop.user.OrderPageDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class OrderController {

    @GetMapping("/order/{userId}")
    public void orderPageGet(@PathVariable("userId")long userId, OrderPageDto opd, Model model,
                             @SessionAttribute(name = "loginUser", required = false) User loginUser){

        if(loginUser == null){
            return;
        }

        System.out.println("userId : " + userId);
        System.out.println("orders : " + opd.getOrders());
    }
}
