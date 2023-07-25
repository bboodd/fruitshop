package com.shop.fruitshop.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @RequestMapping("/testSelect")
    @ResponseBody
    public HashMap<String, Object> testSelect() {
        return userService.testSelect();
    }

}