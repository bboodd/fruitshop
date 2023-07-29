package com.shop.fruitshop.user;

import com.shop.fruitshop.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {


    private final UserService userService;

    @GetMapping("{pathName}")
    public String login(@PathVariable String pathName) {
        return "user/"+pathName;
    }

    @GetMapping("favicon.ico")
    @ResponseBody
    void noFavicon() {
    }

//    @PostMapping("user/join")
//    public String join(@RequestParam HashMap<String, Object> requestData){
//        userService.joinUser(requestData);
//
//        return "user/joinConfirm";
//    }

    @PostMapping("/join")
    public String join(@Valid User user,
                       BindingResult bindingResult,
                       @RequestParam(required = false) List<String> termStatus,
                       Model model){

        if (bindingResult.hasErrors()) {
            return "user/join";
        }

        String joinEmail = userService.join(user, termStatus);

        model.addAttribute("email", joinEmail);

        return "user/joinConfirm";
    }

    @PostMapping("/login")
    public String login(@Valid UserLoginForm form,
                        BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "user/login";
        }

        User loginUser = userService.login(form);

        if (loginUser == null) {
            bindingResult.reject("loginFail", "이메일과 비밀번호가 일치하지 않습니다.");
            return "user/login";
        }

        return "redirect:/";
    }

//    @PostMapping("/emailCheck")
    @RequestMapping("emailCheck")
    @ResponseBody
    public String emailCheck(@RequestBody HashMap<String, String> param){

        int result = userService.emailCheck(param);

        if (result == 1) {
            return "이미 가입된 계정입니다.";
        } else {
            return null;
        }
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