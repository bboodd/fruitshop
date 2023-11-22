package com.shop.fruitshop.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
public class User {
    private Long id;

    @NotBlank(message = "이메일은 필수 정보 입니다.")
    private String email;

    @NotBlank(message = "닉네임은 필수 정보 입니다.")
    private String nickname;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$", message = "영문, 숫자, 특수문자 (8자-20자 이내)로 입력하세요.")
    private String password;

    private int status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}