package com.shop.fruitshop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
public class MainController {

    @GetMapping("")
    public String home(){return "index";}

    @RequestMapping("alert")
    public String alertModal(@RequestBody HashMap<String, Object> param,
                             Model model){
        model.addAttribute("title", param.get("title"));
        model.addAttribute("msg", param.get("msg"));

        return "modal/alert";
    }

}
