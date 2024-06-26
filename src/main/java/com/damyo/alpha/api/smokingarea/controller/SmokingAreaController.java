package com.damyo.alpha.api.smokingarea.controller;

import com.damyo.alpha.api.auth.domain.UserDetailsImpl;
import com.damyo.alpha.api.info.controller.dto.InfoResponse;
import com.damyo.alpha.api.info.service.InfoService;
import com.damyo.alpha.api.picture.controller.dto.PictureResponse;
import com.damyo.alpha.api.picture.service.PictureService;
import com.damyo.alpha.api.smokingarea.controller.dto.*;
import com.damyo.alpha.api.smokingarea.domain.SmokingArea;
import com.damyo.alpha.api.smokingarea.service.SmokingAreaService;
import com.damyo.alpha.api.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/area")
@Tag(name = "SmokingAreaController")
public class SmokingAreaController {
    private final SmokingAreaService smokingAreaService;
    private final PictureService pictureService;
    private final UserService userService;
    private final InfoService infoService;

    @Value("${guest.uuid}")
    private String guestUUID;
    private static final int POST_SA_CONTRIBUTION_INCREMENT = 20;

    // 전체구역
    @GetMapping("/all")
    @Operation(summary="모든 흡연구역 반환", description = "모든 흡연구역 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "흡연구역 정보 반환에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<SmokingAreaListResponse> getSmokingAreas(){
        List<SmokingAreaSummaryResponse> areaResponses = smokingAreaService.findAreaAll();
        return ResponseEntity.ok(new SmokingAreaListResponse(areaResponses));
    }

    // 제보된 흡연구역 추가 기능
    @PostMapping("/postArea")
    @Operation(summary="흡연구역 제보", description = "흡연구역 정보를 제보합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "흡연구역 정보 제보에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<SmokingAreaDetailResponse> postSmokingArea(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Parameter(description = "흡얀구역 정보 요청사항", in = ParameterIn.DEFAULT)
            @RequestBody SmokingAreaRequest areaRequest){
        SmokingArea area =  smokingAreaService.addSmokingArea(areaRequest);

        if(areaRequest.url() != null) {
            if(userDetails != null) {
                pictureService.uploadPicture(userDetails.getId(), area.getId(), areaRequest.url());
                userService.updateContribution(userDetails.getId(), POST_SA_CONTRIBUTION_INCREMENT);
            }
            else{
                pictureService.uploadPicture(UUID.fromString(guestUUID), area.getId(), areaRequest.url());
            }
        }

        return ResponseEntity
                .ok(area.toDTO());
    }

    @GetMapping("/details/{smokingAreaId}")
    @Operation(summary="흡연구역 상세정보", description = "흡연구역 ID의 상세정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "흡연구역 정보 반환에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<SmokingAreaAllResponse> getSmokingAreaDetailsById(
            @Parameter(description = "흡연구역 ID", in = ParameterIn.PATH)
            @PathVariable String smokingAreaId){
        SmokingAreaDetailResponse area = smokingAreaService.findAreaById(smokingAreaId).toDTO();
        InfoResponse info = infoService.getInfo(smokingAreaId);
        List<PictureResponse> picList = pictureService.getPicturesBySmokingArea(smokingAreaId, 10L);

        SmokingAreaAllResponse response = new SmokingAreaAllResponse(area.areaId(), area.name(), area.latitude(), area.longitude(), area.address(),
                area.createdAt(), area.description(), area.score(), area.status(), area.opened(), info.opened(), area.closed(), info.closed(),
                area.hygiene(), info.hygiene(), area.dirty(), info.dirty(), area.airOut(), info.airOut(), area.noExist(), info.notExist(),
                area.indoor(), info.indoor(), area.outdoor(), info.outdoor(), area.big(), info.big(), area.small(), info.small(),
                area.crowded(), info.crowded(), area.quite(), info.quite(), area.chair(), info.chair(), picList);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/summary/{smokingAreaId}")
    @Operation(summary="흡연구역 요약정보", description = "흡연구역 ID의 요약정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "흡연구역 정보 반환에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<SmokingAreaSummaryResponse> getSmokingAreaSummaryById(
            @Parameter(description = "흡연구역 ID", in = ParameterIn.PATH)
            @PathVariable String smokingAreaId){
        SmokingArea areaResponse = smokingAreaService.findAreaById(smokingAreaId);

        return ResponseEntity.ok(areaResponse.toSUM());
    }

    // 특정날짜이후 추가된 구역찾기
    @GetMapping("/dateSearch")
    @Operation(summary="흡연구역 날짜 검색", description = "특정 날짜 이후에 추가된 흡연구역 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "흡연구역 정보 반환에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<SmokingAreaListResponse> getSmokingAreasByCreatedAt(
            @Parameter(description = "기준 날짜", in = ParameterIn.DEFAULT)
            @RequestParam LocalDate createdAt){
        List<SmokingAreaSummaryResponse> areaResponses = smokingAreaService.findAreaByCreatedAt(LocalDateTime.of(createdAt, LocalTime.MIN));
        return ResponseEntity.ok(new SmokingAreaListResponse(areaResponses));
    }

//    검색어로 구역찾기
//    @GetMapping("/area/nameSearch")
//    public ResponseEntity<SmokingAreaListResponse> getSmokingAreasByName(@RequestBody String name){
//        List<SmokingAreaResponse> areaResponses = smokingAreaService.findAreaByName(name);
//        return ResponseEntity.ok(new SmokingAreaListResponse(areaResponses));
//    }

    // 위도 경도에 따른 검색
    @PostMapping("/locateSearch")
    @Operation(summary="흡연구역 좌표 검색", description = "특정 좌표 구역의 흡연구역 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "흡연구역 정보 반환에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<SmokingAreaListResponse> searchSmokingAreaByLocate(
            @Parameter(description = "좌표 검색 요청사항", in = ParameterIn.DEFAULT)
            @RequestBody SearchLocateRequest coordinate){
        List<SmokingAreaSummaryResponse> areaResponseList = smokingAreaService.findAreaByCoordinate(coordinate);
        return ResponseEntity.ok(new SmokingAreaListResponse(areaResponseList));
    }

    // 주소구역에 따른 검색
    @GetMapping("/regionSearch")
    @Operation(summary="흡연구역 지역 검색", description = "특정 지역의 흡연구역 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "흡연구역 정보 반환에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<SmokingAreaListResponse> searchSmokingAreaByRegion(
            @Parameter(description = "검색 지역", in = ParameterIn.DEFAULT)
            @RequestParam String region){
        List<SmokingAreaSummaryResponse> areaResponseList = smokingAreaService.findAreaByRegion(region);
        return ResponseEntity.ok(new SmokingAreaListResponse(areaResponseList));
    }

    // 특정 퀴리에 따른 검색
    @PostMapping("/querySearch")
    @Operation(summary="흡연구역 검색어 검색", description = "특정 검색어로 검색한 흡연구역 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "흡연구역 정보 반환에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<SmokingAreaListResponse> searchSmokingAreaByQuery(
            @Parameter(description = "검색어 검색 요청사항", in = ParameterIn.DEFAULT)
            @RequestBody SearchQueryRequest query){
        List<SmokingAreaSummaryResponse> areaResponseList = smokingAreaService.findAreaByQuery(query);
        return ResponseEntity.ok(new SmokingAreaListResponse(areaResponseList));
    }

}