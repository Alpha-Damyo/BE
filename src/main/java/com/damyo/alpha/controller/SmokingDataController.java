package com.damyo.alpha.controller;

import com.damyo.alpha.dto.request.SmokingDataListRequest;
import com.damyo.alpha.dto.response.StatisticsDateResponse;
import com.damyo.alpha.dto.response.StatisticsRegionResponse;
import com.damyo.alpha.service.SmokingDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class SmokingDataController {
    private final SmokingDataService smokingDataService;

    // 데이터 추가 기능
    @PostMapping("/data/postData")
    public void postSmokingData(@RequestBody SmokingDataListRequest dataListRequest){
        smokingDataService.addSmokingData(dataListRequest);
    }

    // 데이터 구역별 통계 반환
    @GetMapping("/data/regionStatics")
    public ResponseEntity<StatisticsRegionResponse> getSmokingDataStaticsByArea(){
        StatisticsRegionResponse regionResponse = smokingDataService.getStaticsByRegion();
        return ResponseEntity.ok(regionResponse);
    }

    // 데이터 시간별 통계 반환
    @GetMapping("/data/dateStatics")
    public ResponseEntity<StatisticsDateResponse> getSmokingDateStaticsByDate(){
        StatisticsDateResponse dateResponse = smokingDataService.getStaticsByDate();
        return ResponseEntity.ok(dateResponse);
    }

}
