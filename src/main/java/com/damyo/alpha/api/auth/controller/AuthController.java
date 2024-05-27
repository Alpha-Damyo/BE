package com.damyo.alpha.api.auth.controller;


import com.damyo.alpha.api.user.domain.User;
import com.damyo.alpha.api.auth.controller.dto.LoginRequest;
import com.damyo.alpha.api.auth.controller.dto.SignUpRequest;
import com.damyo.alpha.api.auth.controller.dto.TokenResponse;
import com.damyo.alpha.api.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
@Tag(name = "AuthController")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "토큰을 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입에 성공하였습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "1001", description = "이미 가입된 계정이 존재합니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<TokenResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);
        User user = authService.login(signUpRequest);
        String token = authService.generateToken(user);
        return ResponseEntity.ok().body(new TokenResponse(token));
    }


    @PostMapping("/login")
    @Operation(summary = "로그인", description = "토큰을 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입에 성공하였습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "1002", description = "해당 이메일이 존재하지 않습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        User user = authService.login(loginRequest);
        String token = authService.generateToken(user);
        return ResponseEntity.ok().body(new TokenResponse(token));
    }
}
