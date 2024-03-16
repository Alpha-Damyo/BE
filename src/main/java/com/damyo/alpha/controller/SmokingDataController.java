package com.damyo.alpha.controller;

import com.damyo.alpha.dto.SmokingDatasRequest;
import com.damyo.alpha.dto.StaticsRegionResponse;
import com.damyo.alpha.dto.StaticsDateResponse;
import com.damyo.alpha.service.SmokingDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class SmokingDataController {
    private final SmokingDataService smokingDataService;

    // 데이터 추가 기능
    @PostMapping("/smokingData/postData")
    public void postSmokingData(SmokingDatasRequest datasRequest){
        smokingDataService.addSmokingData(datasRequest);
    }

    // 데이터 구역별 통계 반환
    @GetMapping("/smokingData/staticsByRegion")
    public ResponseEntity<StaticsRegionResponse> getSmokingDataStaticsByArea(@RequestParam String region){
        StaticsRegionResponse regionResponse = smokingDataService.getStaticsByRegion(region);
        return ResponseEntity.ok(regionResponse) ;
    }

    // 데이터 시간별 통계 반환
    @GetMapping("/smokingData/staticsByDate")
    public ResponseEntity<StaticsDateResponse> getSmokingDateStaticsByDate(@RequestParam LocalDateTime date){
        StaticsDateResponse dateResponse = smokingDataService.getStaticsByDate(date);
        return ResponseEntity.ok(dateResponse);
    }

    // TODO 기타 기준별 통계 반환
    //  흡연량, 이용자 수, 구역별 이용 시간대
}
