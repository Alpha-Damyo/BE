package com.damyo.alpha.service;

import com.damyo.alpha.domain.SmokingData;
import com.damyo.alpha.domain.User;
import com.damyo.alpha.dto.request.SmokingDataRequest;
import com.damyo.alpha.dto.request.SmokingDataListRequest;
import com.damyo.alpha.repository.SmokingDataRepository;
import com.damyo.alpha.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SmokingDataService {

    private final SmokingDataRepository smokingDataRepository;
    private final UserRepository userRepository;

    public void addSmokingData(SmokingDataListRequest dataListRequest) {
        List<SmokingDataRequest> smokingDataRequests = dataListRequest.dataRequests();
        for(SmokingDataRequest dataRequest : smokingDataRequests) {
            User user = userRepository.findUserByEmail(dataRequest.email()).get();
            smokingDataRepository.save(SmokingData.builder().user(user).createdAt(dataRequest.creatdAt()).smokingArea(dataRequest.smokingArea()).build());
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
