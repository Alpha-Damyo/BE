package com.damyo.alpha.api.smokingdata.controller;

import com.damyo.alpha.api.smokingdata.controller.dto.SmokingDataListRequest;
import com.damyo.alpha.api.smokingdata.service.SmokingDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
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

//    // 데이터 구역별 통계 반환
//    @GetMapping("/data/regionStatics")
//    public ResponseEntity<StaticsRegionResponse> getSmokingDataStaticsByArea(@RequestParam String region){
//        StaticsRegionResponse regionResponse = smokingDataService.getStaticsByRegion(region);
//        return ResponseEntity.ok(regionResponse);
//    }
//
//    // 데이터 시간별 통계 반환
//    @GetMapping("/data/dateStatics")
//    public ResponseEntity<StaticsDateResponse> getSmokingDateStaticsByDate(@RequestParam LocalDateTime date){
//        StaticsDateResponse dateResponse = smokingDataService.getStaticsByDate(date);
//        return ResponseEntity.ok(dateResponse);
//    }

}
