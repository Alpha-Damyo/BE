package com.damyo.alpha.controller;


import com.damyo.alpha.domain.User;
import com.damyo.alpha.dto.request.LoginRequest;
import com.damyo.alpha.dto.request.SignUpRequest;
import com.damyo.alpha.dto.response.TokenResponse;
import com.damyo.alpha.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);
        return ResponseEntity.ok().body("회원 가입 완료");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        User user = authService.login(loginRequest);
        String token = authService.generateToken(user);
        return ResponseEntity.ok().body(new TokenResponse(token));
    }
}
