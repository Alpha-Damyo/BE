package com.damyo.alpha.api.contest.controller;

import com.damyo.alpha.api.auth.domain.UserDetailsImpl;
import com.damyo.alpha.api.contest.service.ContestService;
import com.damyo.alpha.api.picture.controller.dto.AllRankResponse;
import com.damyo.alpha.api.picture.controller.dto.PictureSliceResponse;
import com.damyo.alpha.api.picture.service.PictureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/contest")
@RequiredArgsConstructor
@RestController
@Tag(name = "ContestController")
public class ContestController {
    private final PictureService pictureService;
    private final ContestService contestService;

    @GetMapping("/page")
    @Operation(summary="사진 콘테스트의 사진 반환", description = "페이지네이션을 적용한 사진 URL 리스트가 반환됩니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사진 URL 리스트 반환에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<PictureSliceResponse> getPageContestPicture(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Parameter(description = "페이지네이션 위치를 위한 커서", in = ParameterIn.DEFAULT, required = true)
            @RequestParam Long cursorId,
            @Parameter(description = "정렬 기준", in = ParameterIn.DEFAULT)
            @RequestParam(required = false) String sortBy,
            @Parameter(description = "검색 지역", in = ParameterIn.DEFAULT)
            @RequestParam(required = false) String region) {
        UUID userId = userDetails.getId();
        PictureSliceResponse contestResponse = pictureService.getPageContestPicture(cursorId, sortBy, region, userId);
        return ResponseEntity
                .ok(contestResponse);
    }

    @PutMapping("/like")
    @Operation()
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사진 URL 리스트 반환에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<?> likeContestPicture(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam Long pictureId) {
        UUID userId = userDetails.getId();

        contestService.likeContestPicture(userId, pictureId);
        pictureService.increaseLikeCount(pictureId);

        return ResponseEntity
                .ok(200);
    }

    @PutMapping("/unlike")
    @Operation()
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사진 URL 리스트 반환에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<?> unlikeContestPicture(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam Long pictureId) {
        UUID userId = userDetails.getId();

        contestService.unlikeContestPicture(userId, pictureId);
        pictureService.decreaseLikeCount(pictureId);

        return ResponseEntity
                .ok(200);
    }

    @GetMapping("/ranking")
    @Operation()
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "랭킹 반환에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<?> unlikeContestPicture(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UUID userId = userDetails.getId();
        AllRankResponse response = pictureService.getContestRanking(userId);

        return ResponseEntity
                .ok(response);
    }
}