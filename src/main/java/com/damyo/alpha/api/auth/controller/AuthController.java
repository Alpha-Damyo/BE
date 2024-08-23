package com.damyo.alpha.api.auth.controller;


import com.damyo.alpha.api.auth.controller.dto.ReissueRequest;
import com.damyo.alpha.api.picture.service.S3ImageService;
import com.damyo.alpha.api.auth.controller.dto.TokenResponse;
import com.damyo.alpha.api.auth.service.AuthService;
import com.damyo.alpha.global.response.exception.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
@Slf4j
@Tag(name = "AuthController")
public class AuthController {

    private final AuthService authService;
    private final S3ImageService s3ImageService;

    @PostMapping("/login/{provider}")
    @Operation(summary = "로그인", description = "토큰을 반환한다.", security = @SecurityRequirement(name = "providerToken"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입에 성공함", content = @Content(schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "A102", description = "해당 토큰으로 받아온 providerId DB에 존재하지 않을 때.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<TokenResponse> login(
            @RequestHeader(value = "Authorization", required = false) String authorHeader,
            @Parameter(description = "토큰을 제공한 인증 제공자(google, naver, kakao)", in = ParameterIn.DEFAULT, required = true)
            @PathVariable String provider) {
        log.info("[Auth]: /login/{} | {}", provider, authorHeader);
        String token = authorHeader.substring(7);
        TokenResponse tokenResponse = authService.login(token, provider);
        return ResponseEntity.ok().body(tokenResponse);
    }

    @GetMapping("/token")
    @Operation(summary = "소셜 로그인 생략하고 토큰 발급받기(테스트용)", description = "바뀐 로그인 방식으로 회원가입된 3개의 계정에 대해 토큰을 발급받는다." +
            "'106362899132468449802', '3399007981', 'tmO5sDw_IaLlon7-M7CesK43rgFDdAnogEKq-ubl_9c' 중에 하나를 골라 providerId에 넣는다")
    public ResponseEntity<TokenResponse> getToken(@RequestParam String providerId) {
        log.info("[Info]: /token | {}", providerId);
        return ResponseEntity.ok().body(authService.generateTestToken(providerId));
    }

    @PostMapping("/reissue")
    @Operation(summary = "토큰 재발급", description = "갱신 토큰으로 접큰 토큰과 갱신 토큰을 재발급한다. 이 때 이전 갱신 토큰은 제 기능을 하지 못한다.")
    public ResponseEntity<TokenResponse> reissueToken(
            @RequestBody ReissueRequest reissueRequest
            ) {
        log.info("[Info]: /reissue | {}", reissueRequest);
        TokenResponse tokenResponse = authService.reissue(reissueRequest.refreshToken());
        return ResponseEntity.ok().body(tokenResponse);
    }
}
