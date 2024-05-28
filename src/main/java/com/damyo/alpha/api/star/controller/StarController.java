package com.damyo.alpha.api.star.controller;

import com.damyo.alpha.api.star.controller.dto.AddStarRequest;
import com.damyo.alpha.api.star.controller.dto.StarResponse;
import com.damyo.alpha.api.auth.domain.UserDetailsImpl;
import com.damyo.alpha.api.star.service.StarService;
import com.damyo.alpha.global.exception.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "즐겨찾기 추가")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "즐겨찾기 추가 성공", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "A103", description = "토큰이 만료됐을 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "A104", description = "토큰이 유효하지 않을 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/add")
    public ResponseEntity<?> addStar(@RequestBody AddStarRequest request, @AuthenticationPrincipal UserDetailsImpl details) {
        starService.addStar(request, details);
        return ResponseEntity.ok().body("즐겨찾기 등록 완료");
    }
    // 사용자의 즐겨찾기 목록 조회
    @Operation(summary = "사용자 즐겨찾기 목록 조회", description = "현재 로그인된 사용자의 즐겨찾기들을 리스트로 반환한다")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "즐겨찾기 목록 조회 성공", content = @Content(array = @ArraySchema(schema = @Schema(implementation = StarResponse.class)))),
            @ApiResponse(responseCode = "A103", description = "토큰이 만료됐을 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "A104", description = "토큰이 유효하지 않을 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/list")
    public ResponseEntity<List<StarResponse>> getStarList(@AuthenticationPrincipal UserDetailsImpl details) {
        List<StarResponse> starResponseList = starService.getStarList(details);
        return ResponseEntity.ok().body(starResponseList);
    }

    // 즐겨찾기 제거
    @Operation(summary = "즐겨찾기 삭제", description = "현재 로그인된 사용자의 즐겨찾기중 id값을 기준으로 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "즐겨찾기 목록 조회 성공", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "S101", description = "파라미터로 들어온 starId가 테이블에 없을 때 발생", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "S102", description = "삭제하려는 즐겨찾기가 로그인된 사용자의 것이 아닐 때 발생", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "A103", description = "토큰이 만료됐을 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "A104", description = "토큰이 유효하지 않을 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteStar(@AuthenticationPrincipal UserDetailsImpl details, @RequestParam Long starId) {
        starService.deleteStar(starId, details.getId());
        return ResponseEntity.ok().body("즐겨찾기 제거 완료");
    }
}
