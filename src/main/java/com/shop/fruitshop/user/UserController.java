package com.shop.fruitshop.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    @GetMapping("user/{pathName}")
    public String login(@PathVariable String pathName) {
        return "user/"+pathName;
    }

    @GetMapping("user/favicon.ico")
    @ResponseBody
    void noFavicon() {
    }

//    @PostMapping("user/join")
//    public String join(@RequestParam HashMap<String, Object> requestData){
//        userService.joinUser(requestData);
//
//        return "user/joinConfirm";
//    }

    @PostMapping("user/join")
    public String join(UserVo userVo){

        userService.joinUser(userVo);

        return "user/joinConfirm";
    }


//    @RequestMapping("/testSelect")
//    @ResponseBody
//    public HashMap<String, Object> testSelect() {
//        return userService.testSelect();
//    }


//    @GetMapping("/testSelect")
//    public String test(Model model){
//        model.addAttribute("result",userService.testSelect());
//        return "test";
//    }

//    @RequestMapping("dbAlert")
//    public String alertModal(@RequestBody HashMap<String, Object> param,
//                             Model model){
//        model.addAttribute("title", param.get("title"));
//        model.addAttribute("msg", param.get("msg"));
//
//        return "modal/alert";
//    }

}