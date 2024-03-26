package com.damyo.alpha.dto.request;

import lombok.Getter;

@Getter
public class SignUpRequest {
    private String email;
    private String name;
    private String profileUrl;
    private String gender;
    private int age;
    private String authProvider;

    public boolean isKakao() {
        return "kakao".equals(this.authProvider);
    }

    public void convertToEmail() {
        this.email += "@kakao.com";
    }
}
