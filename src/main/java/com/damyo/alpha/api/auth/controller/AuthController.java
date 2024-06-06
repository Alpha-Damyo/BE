package com.damyo.alpha.api.auth.controller;


import com.damyo.alpha.api.picture.service.S3ImageService;
import com.damyo.alpha.api.user.domain.User;
import com.damyo.alpha.api.auth.controller.dto.LoginRequest;
import com.damyo.alpha.api.auth.controller.dto.SignUpRequest;
import com.damyo.alpha.api.auth.controller.dto.TokenResponse;
import com.damyo.alpha.api.auth.service.AuthService;
import com.damyo.alpha.global.exception.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
@Tag(name = "AuthController")
public class AuthController {

    private final AuthService authService;
    private final S3ImageService s3ImageService;

    @PostMapping(value = "/signup", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "회원가입", description = "토큰을 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입에 성공함", content = @Content(schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "A101", description = "이미 가입된 계정이 존재할 때(email 중복)", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<TokenResponse> signUp(
            @Parameter(description = "프로필 사진", in = ParameterIn.DEFAULT)
            @RequestPart(value = "image", required = false) MultipartFile image,
            @Parameter(description = "회원가입 요청사항", in = ParameterIn.DEFAULT, required = true)
            @RequestPart SignUpRequest signUpRequest) {

        String profileUrl = null;
        if (image != null) {
            profileUrl = s3ImageService.upload(image);
        }

        User user = authService.signUp(signUpRequest, profileUrl);
        String jwt = authService.generateToken(user.getId());
        return ResponseEntity.ok().body(new TokenResponse(jwt));
    }


    @PostMapping("/login/{provider}")
    @Operation(summary = "로그인", description = "토큰을 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입에 성공함", content = @Content(schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "A102", description = "해당 이메일이 DB에 존재하지 않을 때.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<TokenResponse> login(
            @Parameter(description = "로그인 요청사항", in = ParameterIn.DEFAULT, required = true)
            @RequestParam String token,
            @PathVariable String provider) {

        Map<String, Object> userInfo = authService.getUserInfo(provider, token);
        String providerId = authService.getAttributesId(provider, userInfo);
        UUID id = authService.checkIsMember(providerId);
        String jwt = authService.generateToken(id);
        return ResponseEntity.ok().body(new TokenResponse(jwt));
    }
}
