package com.damyo.alpha.controller;

import com.damyo.alpha.dto.request.SmokingAreaListRequest;
import com.damyo.alpha.dto.response.SmokingAreaResponse;
import com.damyo.alpha.dto.response.SmokingAreaListResponse;
import com.damyo.alpha.service.SmokingAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SmokingAreaController {
    private final SmokingAreaService smokingAreaService;

    // 전체구역
    @GetMapping("/area")
    public ResponseEntity<SmokingAreaListResponse> getSmokingAreas(){
        List<SmokingAreaResponse> areaResponses = smokingAreaService.findAreaByCreatedAt(LocalDateTime.of(1000,1,1,1,1,1));
        return ResponseEntity.ok(new SmokingAreaListResponse(areaResponses));
    }

    // TODO 제보된 흡연구역 추가 기능
    @PostMapping("/area/postArea")
    public void postSmokingArea(SmokingAreaListRequest areas){

    }

    // 아이디로 구역정보 불러오기
    @GetMapping("/area/{smokingAreaId}")
    public ResponseEntity<SmokingAreaResponse> getSmokingAreaById(@PathVariable String smokingAreaId){
        SmokingAreaResponse areaResponse = smokingAreaService.findAreaById(smokingAreaId);
        return ResponseEntity.ok(areaResponse);
    }

    // 특정날짜이후 추가된 구역찾기
    @GetMapping("/area/dateSearch")
    public ResponseEntity<SmokingAreaListResponse> getSmokingAreasByCreatedAt(@RequestParam LocalDateTime createdAt){
        List<SmokingAreaResponse> areaResponses = smokingAreaService.findAreaByCreatedAt(createdAt);
        return ResponseEntity.ok(new SmokingAreaListResponse(areaResponses));
    }

    // TODO 변경 -> 검색어로 구역찾기
    @GetMapping("/area/nameSearch")
    public ResponseEntity<SmokingAreaListResponse> getSmokingAreasByName(@RequestParam String name){
        List<SmokingAreaResponse> areaResponses = smokingAreaService.findAreaByName(name);
        return ResponseEntity.ok(new SmokingAreaListResponse(areaResponses));
    }

    //  TODO 임시여부에 따른 검색
//    @GetMapping("area/statusSearch")
//    public ResponseEntity<SmokingAreasResponse> searchSmokingArea(){
//
//    }

    // TODO 위도 경도에 따른 검색
//    @GetMapping("area/locateSearch")
//    public ResponseEntity<SmokingAreasResponse> searchSmokingArea(){
//
//    }

    // TODO 주소구역에 따른 검색
//    @GetMapping("area/addressSearch")
//    public ResponseEntity<SmokingAreasResponse> searchSmokingArea(){
//
//    }

    // TODO 특정 퀴리에 따른 검색

}