package com.damyo.alpha.dto.request;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String email;
    private String authProvider;

    public boolean isKakao() {
        return "kakao".equals(this.authProvider);
    }

    public void convertToEmail() {
        this.email += "@kakao.com";
    }
}
