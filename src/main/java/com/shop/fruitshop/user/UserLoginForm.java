package com.shop.fruitshop.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserLoginForm {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
