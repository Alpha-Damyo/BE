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

    private HourlyStatisticsResponse getHourlyStatistics() {
        List<SmokingData> dataList = smokingDataRepository.findSmokingDataByCreateAt(LocalDateTime.now().minusYears(1), LocalDateTime.now());

        Integer count0To3 = 0;
        Integer count3To6 = 0;
        Integer count6To9 = 0;
        Integer count9To12 = 0;
        Integer count12To15 = 0;
        Integer count15To18 = 0;
        Integer count18To21 = 0;
        Integer count21To0 = 0;

        for(SmokingData data : dataList){
            int h = data.getCreatedAt().getHour();
            if(0 <= h && h < 3) count0To3 += 1;
            else if(3 <= h && h < 6) count3To6 += 1;
            else if(6 <= h && h < 9) count6To9 += 1;
            else if(9 <= h && h < 12) count9To12 += 1;
            else if(12 <= h && h < 15) count12To15 += 1;
            else if(15 <= h && h < 18) count15To18 += 1;
            else if(18 <= h && h < 21) count18To21 += 1;
            else if(21 <= h) count21To0 += 1;
        }

        return new HourlyStatisticsResponse(count0To3, count3To6, count6To9, count9To12, count12To15, count15To18, count18To21, count21To0);
    }

    private DailyStatisticsResponse getDailyStatistics() {
        List<SmokingData> dataList = smokingDataRepository.findSmokingDataByCreateAt(LocalDateTime.now().minusYears(1), LocalDateTime.now());
        Integer[] days = new Integer[32];

        for(SmokingData data : dataList) {
            int d = data.getCreatedAt().getDayOfMonth();
            days[d] += 1;
        }

        return new DailyStatisticsResponse(days);
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