package com.damyo.alpha.api.smokingdata.controller;

import com.damyo.alpha.api.smokingdata.controller.dto.SmokingDataListRequest;
import com.damyo.alpha.api.smokingdata.controller.dto.StatisticsDateResponse;
import com.damyo.alpha.api.smokingdata.controller.dto.StatisticsRegionResponse;
import com.damyo.alpha.api.smokingdata.service.SmokingDataService;
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
        StatisticsRegionResponse regionResponse = smokingDataService.getStatisticsByRegion();
        return ResponseEntity.ok(regionResponse);
    }

    // 데이터 시간별 통계 반환
    @GetMapping("/data/dateStatics")
    public ResponseEntity<StatisticsDateResponse> getSmokingDateStaticsByDate(){
        StatisticsDateResponse dateResponse = smokingDataService.getStatisticsByDate();
        return ResponseEntity.ok(dateResponse);
    }

}
