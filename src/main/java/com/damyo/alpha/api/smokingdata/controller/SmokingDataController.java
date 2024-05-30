package com.damyo.alpha.api.smokingdata.controller;

import com.damyo.alpha.api.auth.domain.UserDetailsImpl;
import com.damyo.alpha.api.smokingdata.controller.dto.StatisticsDateResponse;
import com.damyo.alpha.api.smokingdata.controller.dto.StatisticsRegionResponse;
import com.damyo.alpha.api.smokingdata.service.SmokingDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/data")
@Tag(name = "SmokingDataController")
public class SmokingDataController {
    private final SmokingDataService smokingDataService;

    // 데이터 추가 기능
    @PostMapping("/postData")
    @Operation(summary="흡연데이터 추가", description = "흡연데이터를 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "흡연데이터 추가에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    public void postSmokingData(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Parameter(description = "흡연데이터 요청사항", in = ParameterIn.DEFAULT)
            @RequestParam String areaId){
        UUID userId = userDetails.getId();
        smokingDataService.addSmokingData(userId, areaId);
    }

    // 데이터 구역별 통계 반환
    @GetMapping("/regionStatics")
    @Operation(summary="주소 관련 통계", description = "주소 관련 통계를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "흡연 통계 반환에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<StatisticsRegionResponse> getSmokingDataStaticsByArea(){
        StatisticsRegionResponse regionResponse = smokingDataService.getStatisticsByRegion();
        return ResponseEntity.ok(regionResponse);
    }

    // 데이터 시간별 통계 반환
    @GetMapping("/dateStatics")
    @Operation(summary="시간 관련 통계", description = "시간 관련 통계를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "흡연 통계 반환에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<StatisticsDateResponse> getSmokingDateStaticsByDate(){
        StatisticsDateResponse dateResponse = smokingDataService.getStatisticsByDate();
        return ResponseEntity.ok(dateResponse);
    }

}
