package com.damyo.alpha.service;

import com.damyo.alpha.domain.SmokingData;
import com.damyo.alpha.dto.request.SmokingDataRequest;
import com.damyo.alpha.dto.request.SmokingDataListRequest;
import com.damyo.alpha.dto.response.*;
import com.damyo.alpha.repository.SmokingDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    //TODO 지역활용 통계
    public StatisticsRegionResponse getStatisticsByRegion() {

    }

    //TODO 전체 지역 통계 기능
    private AllRegionStatisticsResponse getAllRegion() {
        List<SmokingData> dataList = smokingDataRepository.findSmokingDataByCreateAt(LocalDateTime.now().minusYears(1), LocalDateTime.now());
    }

    //TODO 구역 top3 통계 기능
    private AreaTopResponse getAreaTop() {

    }

    //TODO 지역 top3 통계 기능
    private RegionTopResponse getRegionTop() {

    }

    //TODO 시간활용 통계
    public StatisticsDateResponse getStatisticsByDate() {

    }

    //TODO 시간대별 3시간 평균 통계 기능
    private HourlyStatisticsResponse getHourlyStatistics() {
        List<SmokingData> dataList = smokingDataRepository.findSmokingDataByCreateAt(LocalDateTime.now().minusYears(1), LocalDateTime.now());

    }

    //TODO 일단위 평균 통계 기능
    private DailyStatisticsResponse getDailyStatistics() {

    }

    //TODO 주단위 평균 통계 기능
    private  WeeklyStatisticsResponse getWeeklyStatistics() {

    }

    //TODO 월단위 평균 통계 기능
    private MonthlyStatisticsResponse getMonthlyStatistics() {

    }

    //TODO 요일별 평균 통계 기능
    private DayOfWeekStatisticsResponse getDayWeekStatistics() {

    }

}
