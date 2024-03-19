package com.damyo.alpha.service;

import com.damyo.alpha.domain.SmokingData;
import com.damyo.alpha.dto.request.SmokingDataRequest;
import com.damyo.alpha.dto.request.SmokingDatasRequest;
import com.damyo.alpha.repository.SmokingDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SmokingDataService {

    private final SmokingDataRepository smokingDataRepository;


    public void addSmokingData(SmokingDatasRequest datasRequest) {
        List<SmokingDataRequest> smokingDataRequests = datasRequest.dataRequests();
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
