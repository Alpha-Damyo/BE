package com.damyo.alpha.controller;

import com.damyo.alpha.dto.request.UpdateUserNameRequest;
import com.damyo.alpha.dto.request.UpdateUserProfileRequest;
import com.damyo.alpha.dto.request.UpdateUserScoreRequest;
import com.damyo.alpha.dto.response.UserResponse;
import com.damyo.alpha.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID userId) {
        UserResponse user = userService.getUser(userId);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/update/name")
    public ResponseEntity<?> updateName(@RequestBody @Valid UpdateUserNameRequest request) {
        userService.updateName(request);
        return ResponseEntity.ok().body("이름 변경 완료");
    }

    @PutMapping("/update/profile")
    public ResponseEntity<?> updateProfile(@RequestBody @Valid UpdateUserProfileRequest request) {
        userService.updateProfile(request);
        return ResponseEntity.ok().body("프로필 변경 완료");
    }

    @PutMapping("/update/score")
    public ResponseEntity<?> updateScore(@RequestBody @Valid UpdateUserScoreRequest request) {
        userService.updateContribution(request);
        return ResponseEntity.ok().body("기여도 변경 완료");
    }

    @GetMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().body("회원 삭제 완료");
    }

}
