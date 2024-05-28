package com.damyo.alpha.api.star.controller;

import com.damyo.alpha.api.star.controller.dto.AddStarRequest;
import com.damyo.alpha.api.star.controller.dto.StarResponse;
import com.damyo.alpha.api.auth.domain.UserDetailsImpl;
import com.damyo.alpha.api.star.service.StarService;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/star")
@Tag(name = "StarController")
public class StarController {
    private final StarService starService;

    // 즐겨찾기 등록
    @PostMapping("/add")
    public ResponseEntity<?> addStar(@RequestBody AddStarRequest request, @AuthenticationPrincipal UserDetailsImpl details) {
        starService.addStar(request, details);
        return ResponseEntity.ok().body("즐겨찾기 등록 완료");
    }

    // 사용자의 즐겨찾기 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<StarResponse>> getStarList(@AuthenticationPrincipal UserDetailsImpl details) {
        List<StarResponse> starResponseList = starService.getStarList(details);
        return ResponseEntity.ok().body(starResponseList);
    }

    // 즐겨찾기 제거
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteStar(@AuthenticationPrincipal UserDetailsImpl details, @RequestParam Long starId) {
        starService.deleteStar(starId, details.getId());
        return ResponseEntity.ok().body("즐겨찾기 제거 완료");
    }
}
