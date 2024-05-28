package com.damyo.alpha.api.user.controller;

import com.damyo.alpha.api.auth.domain.UserDetailsImpl;
import com.damyo.alpha.api.user.controller.dto.UserResponse;
import com.damyo.alpha.api.user.service.UserService;
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

    @GetMapping("/")
    public ResponseEntity<UserResponse> getUser(@AuthenticationPrincipal UserDetailsImpl details) {
        UserResponse user = userService.getUser(details.getId());
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/update/name")
    public ResponseEntity<?> updateName(@AuthenticationPrincipal UserDetailsImpl details, @RequestParam String name) {
        userService.updateName(details, name);
        return ResponseEntity.ok().body("이름 변경 완료");
    }

    @PutMapping("/update/profile")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal UserDetailsImpl details, @RequestParam String profileUrl) {
        userService.updateProfile(details, profileUrl);
        return ResponseEntity.ok().body("프로필 변경 완료");
    }

    @PutMapping("/update/score")
    public ResponseEntity<?> updateScore(@AuthenticationPrincipal UserDetailsImpl details, @RequestParam int increment) {
        userService.updateContribution(details.getId(), increment);
        return ResponseEntity.ok().body("기여도 변경 완료");
    }

    @GetMapping("/delete")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserDetailsImpl details) {
        userService.deleteUser(details.getId());
        return ResponseEntity.ok().body("회원 삭제 완료");
    }

}
