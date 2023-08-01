package com.shop.fruitshop;

import com.shop.fruitshop.domain.Admin;
import com.shop.fruitshop.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                        Model model) {
        if (loginUser == null) {
            return "index";
        }

        model.addAttribute("user", loginUser);
        return "loginIndex";
    }

    @GetMapping("/admin")
    public String adminIndex(@SessionAttribute(name = "loginAdmin", required = false) Admin loginAdmin, Model model) {

        if (loginAdmin == null) {
            return "adminIndex";
        }

        model.addAttribute("admin", loginAdmin);
        return "admin/dashboard";
    }

}
