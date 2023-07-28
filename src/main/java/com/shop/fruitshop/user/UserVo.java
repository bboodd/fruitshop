package com.shop.fruitshop.user;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserVo {

    //아이디 닉네임 비밀번호

    private String email;

    private String nickname;

    private String password;
}
