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

        Integer[] time = new Integer[8];

        for(SmokingData data : dataList){
            int h = data.getCreatedAt().getHour();
            if(h < 3) time[0] += 1;
            else if(h < 6) time[1] += 1;
            else if(h < 9) time[2] += 1;
            else if(h < 12) time[3] += 1;
            else if(h < 15) time[4] += 1;
            else if(h < 18) time[5] += 1;
            else if(h < 21) time[6] += 1;
            else time[7] += 1;
        }

        return new HourlyStatisticsResponse(time);
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
        List<SmokingData> dataList = smokingDataRepository.findSmokingDataByCreateAt(LocalDateTime.now().minusYears(1), LocalDateTime.now());

        Integer week1 = 0;
        Integer week2 = 0;
        Integer week3 = 0;
        Integer week4 = 0;

        for(SmokingData data : dataList) {
            int d = data.getCreatedAt().getDayOfMonth();
            if(d <= 7) week1 += 1;
            else if(d <= 15) week2 += 1;
            else if(d <= 23) week3 += 1;
            else week4 += 1;
        }

        return new WeeklyStatisticsResponse(week1, week2, week3, week4);
    }

    private MonthlyStatisticsResponse getMonthlyStatistics() {
        List<SmokingData> dataList = smokingDataRepository.findSmokingDataByCreateAt(LocalDateTime.now().minusYears(1), LocalDateTime.now());

        Integer[] month = new Integer[13];

        for(SmokingData data : dataList) {
            int m = data.getCreatedAt().getMonthValue();
            month[m] += 1;
        }

        return new MonthlyStatisticsResponse(month);
    }

    //TODO 요일별 평균 통계 기능
    private DayOfWeekStatisticsResponse getDayWeekStatistics() {

    }

}