package com.damyo.alpha.api.user.controller;

import com.damyo.alpha.api.auth.domain.UserDetailsImpl;
import com.damyo.alpha.api.user.controller.dto.UserResponse;
import com.damyo.alpha.api.user.service.UserService;
import com.damyo.alpha.global.exception.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
@Tag(name = "UserController")
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저 정보 조회", description = "유저의 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정보 조회에 성공", content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "U101", description = "UUID값과 일치하는 user가 없는 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/info")
    public ResponseEntity<UserResponse> getUser(@AuthenticationPrincipal UserDetailsImpl details) {
        UserResponse user = userService.getUser(details.getId());
        return ResponseEntity.ok().body(user);
    }

    @Operation(summary = "유저 이름 변경", description = "유저의 이름을 변경한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이름 변경에 성공", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "U102", description = "기존 이름과 변경하려는 이름이 동일한 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/update/name")
    public ResponseEntity<?> updateName(@AuthenticationPrincipal UserDetailsImpl details, @RequestParam String name) {
        userService.updateName(details, name);
        return ResponseEntity.ok().body("이름 변경 완료");
    }

    @Operation(summary = "유저 프로필 변경", description = "유저의 프로필을 변경한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 변경에 성공", content = @Content(schema = @Schema(implementation = String.class))),
    })
    @PutMapping("/update/profile")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal UserDetailsImpl details, @RequestParam String profileUrl) {
        userService.updateProfile(details, profileUrl);
        return ResponseEntity.ok().body("프로필 변경 완료");
    }

    @Operation(summary = "유저 기여도 변경", description = "유저의 기여도를 변경한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "기여도 변경에 성공", content = @Content(schema = @Schema(implementation = String.class))),
    })
    @PutMapping("/update/score")
    public ResponseEntity<?> updateScore(@AuthenticationPrincipal UserDetailsImpl details, @RequestParam int increment) {
        userService.updateContribution(details.getId(), increment);
        return ResponseEntity.ok().body("기여도 변경 완료");
    }

    @Operation(summary = "유저 삭제", description = "유저를 DB에서 지운다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 삭제에 성공", content = @Content(schema = @Schema(implementation = String.class))),
    })
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserDetailsImpl details) {
        userService.deleteUser(details.getId());
        return ResponseEntity.ok().body("회원 삭제 완료");
    }

}
