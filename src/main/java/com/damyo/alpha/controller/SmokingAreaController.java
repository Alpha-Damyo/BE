package com.damyo.alpha.controller;

import com.damyo.alpha.dto.SmokingAreaResponse;
import com.damyo.alpha.dto.SmokingAreasResponse;
import com.damyo.alpha.service.SmokingAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SmokingAreaController {
    private final SmokingAreaService smokingAreaService;

    // 전체구역
    @GetMapping("/smokingArea")
    public ResponseEntity<SmokingAreasResponse> getSmokingAreas(){
        List<SmokingAreaResponse> areaResponses = smokingAreaService.findAreaByCreatedAt(LocalDateTime.of(1000,1,1,1,1,1));
        return ResponseEntity.ok(new SmokingAreasResponse(areaResponses));
    }

    // 아이디로 구역찾기
    @GetMapping("/smokingArea/{smokingAreaId}")
    public ResponseEntity<SmokingAreaResponse> getSmokingAreaById(@PathVariable String smokingAreaId){
        SmokingAreaResponse areaResponse = smokingAreaService.findAreaById(smokingAreaId);
        return ResponseEntity.ok(areaResponse);
    }

    // 특정날짜이후 추가된 구역찾기
    @GetMapping("/smokingArea/searchByDate")
    public ResponseEntity<SmokingAreasResponse> getSmokingAreasByCreatedAt(@RequestParam LocalDateTime createdAt){
        List<SmokingAreaResponse> areaResponses = smokingAreaService.findAreaByCreatedAt(createdAt);
        return ResponseEntity.ok(new SmokingAreasResponse(areaResponses));
    }

    // 이름으로 구역찾기
    @GetMapping("/smokingArea/searchByName")
    public ResponseEntity<SmokingAreasResponse> getSmokingAreasByName(@RequestParam String name){
        List<SmokingAreaResponse> areaResponses = smokingAreaService.findAreaByName(name);
        return ResponseEntity.ok(new SmokingAreasResponse(areaResponses));
    }

    // TODO 임시여부에 따른 검색
//    @GetMapping("smokingArea/status")
//    public ResponseEntity<SmokingAreasResponse> searchSmokingArea(){
//
//    }

    // TODO 위도 경도에 따른 검색 -> Float 말고 BigDecimal이 맞나?
//    @GetMapping("smokingArea/locate")
//    public ResponseEntity<SmokingAreasResponse> searchSmokingArea(){
//
//    }

    // TODO 주소 구역에 따른 검색 -> 주소 구역 과 구역ID를 어떻게 정하는가?
//    @GetMapping("smokingArea/address")
//    public ResponseEntity<SmokingAreasResponse> searchSmokingArea(){
//
//    }

    // TODO 특정 퀴리에 따른 검색???
    // TODO 데이터 추가 및 업데이트 -> 별도로 작성? 굳이 컨트롤러 X 그냥 서비스만?
    //
}
