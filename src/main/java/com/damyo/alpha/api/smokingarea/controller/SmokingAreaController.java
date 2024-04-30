package com.damyo.alpha.api.smokingarea.controller;

import com.damyo.alpha.api.smokingarea.controller.dto.SearchLocateRequest;
import com.damyo.alpha.api.smokingarea.controller.dto.SearchQueryRequest;
import com.damyo.alpha.api.smokingarea.controller.dto.SmokingAreaListRequest;
import com.damyo.alpha.api.smokingarea.controller.dto.SmokingAreaResponse;
import com.damyo.alpha.api.smokingarea.controller.dto.SmokingAreaListResponse;
import com.damyo.alpha.api.smokingarea.service.SmokingAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SmokingAreaController {
    private final SmokingAreaService smokingAreaService;

    // 전체구역
    @GetMapping("/area")
    public ResponseEntity<SmokingAreaListResponse> getSmokingAreas(){
        List<SmokingAreaResponse> areaResponses = smokingAreaService.findAreaAll();
        return ResponseEntity.ok(new SmokingAreaListResponse(areaResponses));
    }

    // 제보된 흡연구역 추가 기능
    @PostMapping("/area/postArea")
    public void postSmokingArea(@RequestBody SmokingAreaListRequest areaListRequest){
        smokingAreaService.addSmokingArea(areaListRequest);
    }

    // 아이디로 구역정보 불러오기
    @GetMapping("/area/{smokingAreaId}")
    public ResponseEntity<SmokingAreaResponse> getSmokingAreaById(@PathVariable String smokingAreaId){
        SmokingAreaResponse areaResponse = smokingAreaService.findAreaById(smokingAreaId);
        return ResponseEntity.ok(areaResponse);
    }

    // 특정날짜이후 추가된 구역찾기
    @GetMapping("/area/dateSearch")
    public ResponseEntity<SmokingAreaListResponse> getSmokingAreasByCreatedAt(@RequestBody LocalDateTime createdAt){
        List<SmokingAreaResponse> areaResponses = smokingAreaService.findAreaByCreatedAt(createdAt);
        return ResponseEntity.ok(new SmokingAreaListResponse(areaResponses));
    }

    // 검색어로 구역찾기
//    @GetMapping("/area/nameSearch")
//    public ResponseEntity<SmokingAreaListResponse> getSmokingAreasByName(@RequestBody String name){
//        List<SmokingAreaResponse> areaResponses = smokingAreaService.findAreaByName(name);
//        return ResponseEntity.ok(new SmokingAreaListResponse(areaResponses));
//    }

    // 위도 경도에 따른 검색
    @GetMapping("/area/locateSearch")
    public ResponseEntity<SmokingAreaListResponse> searchSmokingAreaByLocate(@RequestBody SearchLocateRequest coordinate){
        BigDecimal latitude = coordinate.latitude();
        BigDecimal longitude = coordinate.longitude();
        BigDecimal range = coordinate.range();

        List<SmokingAreaResponse> areaResponseList = smokingAreaService.findAreaByCoordinate(latitude, longitude, range);
        return ResponseEntity.ok(new SmokingAreaListResponse(areaResponseList));
    }

    // 주소구역에 따른 검색
    @GetMapping("/area/regionSearch")
    public ResponseEntity<SmokingAreaListResponse> searchSmokingAreaByRegion(@RequestBody String region){
        List<SmokingAreaResponse> areaResponseList = smokingAreaService.findAreaByRegion(region);
        return ResponseEntity.ok(new SmokingAreaListResponse(areaResponseList));
    }

    // 특정 퀴리에 따른 검색
    @GetMapping("/area/querySearch")
    public ResponseEntity<SmokingAreaListResponse> searchSmokingAreaByQuery(@RequestBody SearchQueryRequest query){
        List<SmokingAreaResponse> areaResponseList = smokingAreaService.findAreaByQuery(query);
        return ResponseEntity.ok(new SmokingAreaListResponse(areaResponseList));
    }

}