package com.damyo.alpha.api.smokingdata.controller;

import com.damyo.alpha.api.smokingdata.controller.dto.SmokingDataListRequest;
import com.damyo.alpha.api.smokingdata.controller.dto.StatisticsDateResponse;
import com.damyo.alpha.api.smokingdata.controller.dto.StatisticsRegionResponse;
import com.damyo.alpha.api.smokingdata.service.SmokingDataService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/data")
@Tag(name = "SmokingDataController")
public class SmokingDataController {
    private final SmokingDataService smokingDataService;

    // 데이터 추가 기능
    @PostMapping("/postData")
    public void postSmokingData(@RequestBody SmokingDataListRequest dataListRequest){
        smokingDataService.addSmokingData(dataListRequest);
    }

    // 데이터 구역별 통계 반환
    @GetMapping("/regionStatics")
    public ResponseEntity<StatisticsRegionResponse> getSmokingDataStaticsByArea(){
        StatisticsRegionResponse regionResponse = smokingDataService.getStatisticsByRegion();
        return ResponseEntity.ok(regionResponse);
    }

    // 데이터 시간별 통계 반환
    @GetMapping("/dateStatics")
    public ResponseEntity<StatisticsDateResponse> getSmokingDateStaticsByDate(){
        StatisticsDateResponse dateResponse = smokingDataService.getStatisticsByDate();
        return ResponseEntity.ok(dateResponse);
    }

}
