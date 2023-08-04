package com.shop.fruitshop.admin;

import com.shop.fruitshop.domain.Admin;
import com.shop.fruitshop.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    private final AdminService adminService;

    @GetMapping("{pathName}")
    public String path(@PathVariable String pathName,
                       @SessionAttribute(name = "loginAdmin", required = false) Admin loginAdmin,
                       Model model) {
        if (loginAdmin == null) {
            return "adminIndex";
        }

        model.addAttribute("admin", loginAdmin);
        return "admin/"+pathName;
    }

    @GetMapping("favicon.ico")
    @ResponseBody
    void noFavicon() {
    }

    @PostMapping("/login")
    public String login(Admin admin, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()){
            return "adminIndex";
        }

        HttpSession session = request.getSession();

        Admin loginAdmin = adminService.findById(admin.getId());

        if (loginAdmin != null) {
            String rawPw = admin.getPassword();
            String encodePw = loginAdmin.getPassword();

            if (rawPw.equals(encodePw)) {   //비밀번호 일치 여부 판단
                session.setAttribute("loginAdmin", loginAdmin);
            }
        }
        return "redirect:/admin";
    }

    @PostMapping("/addProduct")
    public String addProduct(@Valid addProductForm form, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "admin/addProduct";
        }
        adminService.addProduct(form);

        return "admin/product";
    }
}
