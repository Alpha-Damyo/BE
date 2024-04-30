package com.damyo.alpha.api.smokingdata.service;

import com.damyo.alpha.api.smokingdata.domain.SmokingData;
import com.damyo.alpha.api.smokingdata.controller.dto.SmokingDataRequest;
import com.damyo.alpha.api.smokingdata.controller.dto.SmokingDataListRequest;
import com.damyo.alpha.api.smokingdata.domain.SmokingDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SmokingDataService {

    private final SmokingDataRepository smokingDataRepository;

    public void addSmokingData(SmokingDataListRequest dataListRequest) {
        List<SmokingDataRequest> smokingDataRequests = dataListRequest.dataRequests();
        for(SmokingDataRequest dataRequest : smokingDataRequests) {
            smokingDataRepository.save(SmokingData.builder().user(dataRequest.user()).createdAt(dataRequest.creatdAt()).smokingArea(dataRequest.smokingArea()).build());
        }
    }

//    public StaticsRegionResponse getStaticsByRegion(String region) {
//        return new StaticsRegionResponse();
//    }
//
//    public StaticsDateResponse getStaticsByDate(LocalDateTime date) {
//        return new StaticsDateResponse();
//    }
}
