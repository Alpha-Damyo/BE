package com.damyo.alpha.api.user.controller;

import com.damyo.alpha.api.auth.domain.UserDetailsImpl;
import com.damyo.alpha.api.picture.service.S3ImageService;
import com.damyo.alpha.api.smokingarea.service.SmokingAreaService;
import com.damyo.alpha.api.user.controller.dto.ReportRequest;
import com.damyo.alpha.api.user.controller.dto.UserResponse;
import com.damyo.alpha.api.user.service.UserService;
import com.damyo.alpha.global.response.CommonSuccessApiResponse;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
@Tag(name = "UserController")
@SecurityRequirement(name = "jwt")
@Slf4j
public class UserController {

    private final UserService userService;
    private final S3ImageService s3ImageService;
    private final SmokingAreaService smokingAreaService;

    @Operation(summary = "유저 정보 조회", description = "유저의 정보를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정보 조회에 성공", content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "U101", description = "UUID값과 일치하는 user가 없는 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "A103", description = "토큰이 만료됐을 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "A104", description = "토큰이 유효하지 않을 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/info")
    public ResponseEntity<UserResponse> getUser(@AuthenticationPrincipal UserDetailsImpl details) {
        log.info("[User]: /info");
        UserResponse user = userService.getUser(details.getId());
        return ResponseEntity.ok().body(user);
    }

    @Operation(summary = "유저 이름 변경", description = "유저의 이름을 변경한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이름 변경에 성공", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "U102", description = "기존 이름과 변경하려는 이름이 동일한 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "A103", description = "토큰이 만료됐을 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "A104", description = "토큰이 유효하지 않을 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/update/name")
    public ResponseEntity<?> updateName(@AuthenticationPrincipal UserDetailsImpl details, @Schema(description = "변경할 이름") @RequestParam String name) {
        log.info("[User]: /update/name | {}", name);
        userService.updateName(details, name);
        return ResponseEntity.ok().body(new CommonSuccessApiResponse("이름 변경 완료"));
    }

    @Operation(summary = "유저 프로필 변경", description = "유저의 프로필을 변경한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 변경에 성공", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "A103", description = "토큰이 만료됐을 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "A104", description = "토큰이 유효하지 않을 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping(value = "/update/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal UserDetailsImpl details,
                                           @Parameter(description = "변경할 프로필 사진", in = ParameterIn.DEFAULT)
                                           @RequestPart(value = "image") MultipartFile profile
                                           ) {
        log.info("[User]: /update/profile");
        String profileUrl = s3ImageService.upload(profile);
        String prevUrl = userService.updateProfile(details, profileUrl);
        s3ImageService.deleteImageFromS3(prevUrl);
        return ResponseEntity.ok().body(new CommonSuccessApiResponse("프로필 변경 완료"));
    }

    @Operation(summary = "유저 기여도 변경", description = "유저의 기여도를 변경한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "기여도 변경에 성공", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "A103", description = "토큰이 만료됐을 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "A104", description = "토큰이 유효하지 않을 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/update/score")
    public ResponseEntity<?> updateScore(@AuthenticationPrincipal UserDetailsImpl details, @Schema(description = "기여도 증가량") @RequestParam int increment) {
        log.info("[User]: /update/score");
        userService.updateContribution(details.getId(), increment);
        return ResponseEntity.ok().body(new CommonSuccessApiResponse("기여도 변경 완료"));
    }

    @Operation(summary = "유저 삭제", description = "유저를 DB에서 지운다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 삭제에 성공", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "A103", description = "토큰이 만료됐을 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "A104", description = "토큰이 유효하지 않을 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserDetailsImpl details) {
        log.info("[User]: /delete");
        userService.deleteUser(details.getId());
        return ResponseEntity.ok().body(new CommonSuccessApiResponse("회원 삭제 완료"));
    }

    @PostMapping("/report/{smokingAreaId}")
    @Operation(summary = "흡연구역 신고", description = "흡연구역이 존재하지 않을 때 유저가 보내는 요청")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "흡연구역 신고 성공"),
            @ApiResponse(responseCode = "SA102", description = "이미 신고한 흡연구역을 다시 신고한 경우")
    })
    public ResponseEntity<?> reportSmokingArea(@PathVariable String smokingAreaId, @AuthenticationPrincipal UserDetailsImpl details,
                                               @RequestBody ReportRequest reportRequest) {
        log.info("[User]: /report/{}", smokingAreaId);
        smokingAreaService.handleReport(smokingAreaId, details.getId(), reportRequest);
        return ResponseEntity.ok().body(new CommonSuccessApiResponse("신고 완료"));
    }
}
