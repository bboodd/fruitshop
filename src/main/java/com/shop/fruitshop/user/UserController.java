package com.shop.fruitshop.user;

import com.shop.fruitshop.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    //회원가입
    @PostMapping("/join")
    public String join(@Valid User user,
                       BindingResult bindingResult,
                       @RequestParam(required = false) List<String> termStatus,
                       RedirectAttributes re){

        if (bindingResult.hasErrors()) {
            return "user/join";
        }

        String joinEmail = userService.join(user, termStatus);

        re.addFlashAttribute("email", joinEmail);

        return "redirect:/user/joinConfirm?email={email}";
    }

    //로그인
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

    //이메일 중복 체크
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

    //닉네임 중복 체크
    @RequestMapping("nicknameCheck")
    @ResponseBody
    public String nicknameCheck(@RequestBody HashMap<String, String> param){

        int result = userService.nicknameCheck(param);

        if (result == 1) {
            return "해당 닉네임은 이미 사용 중입니다.";
        } else {
            return null;
        }
    }

}