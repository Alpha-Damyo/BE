package com.damyo.alpha.service;

import com.damyo.alpha.domain.SmokingData;
import com.damyo.alpha.dto.request.SmokingDataRequest;
import com.damyo.alpha.dto.request.SmokingDataListRequest;
import com.damyo.alpha.dto.response.*;
import com.damyo.alpha.repository.SmokingDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
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

    public StatisticsDateResponse getStatisticsByDate() {
        List<SmokingData> dataList = smokingDataRepository.findSmokingDataByCreateAt(LocalDateTime.now().minusYears(1), LocalDateTime.now());


        return new StatisticsDateResponse(getHourlyStatistics(dataList), getDailyStatistics(dataList), getWeeklyStatistics(dataList), getMonthlyStatistics(dataList), getDayWeekStatistics(dataList));
    }

    private HourlyStatisticsResponse getHourlyStatistics(List<SmokingData> dataList) {
        Integer[] time = new Integer[9];

        for(SmokingData data : dataList){
            int h = data.getCreatedAt().getHour();
            if(h < 3) time[1] += 1;
            else if(h < 6) time[2] += 1;
            else if(h < 9) time[3] += 1;
            else if(h < 12) time[4] += 1;
            else if(h < 15) time[5] += 1;
            else if(h < 18) time[6] += 1;
            else if(h < 21) time[7] += 1;
            else time[8] += 1;
        }

        return new HourlyStatisticsResponse(time);
    }

    private DailyStatisticsResponse getDailyStatistics(List<SmokingData> dataList) {
        Integer[] days = new Integer[32];

        for(SmokingData data : dataList) {
            int d = data.getCreatedAt().getDayOfMonth();
            days[d] += 1;
        }

        return new DailyStatisticsResponse(days);
    }

    private  WeeklyStatisticsResponse getWeeklyStatistics(List<SmokingData> dataList) {
        Integer[] week = new Integer[5];

        for(SmokingData data : dataList) {
            int d = data.getCreatedAt().getDayOfMonth();
            if(d <= 7) week[1] += 1;
            else if(d <= 15) week[2] += 1;
            else if(d <= 23) week[3] += 1;
            else week[4] += 1;
        }

        return new WeeklyStatisticsResponse(week);
    }

    private MonthlyStatisticsResponse getMonthlyStatistics(List<SmokingData> dataList) {
        Integer[] month = new Integer[13];

        for(SmokingData data : dataList) {
            int m = data.getCreatedAt().getMonthValue();
            month[m] += 1;
        }

        return new MonthlyStatisticsResponse(month);
    }

    private DayOfWeekStatisticsResponse getDayWeekStatistics(List<SmokingData> dataList) {
        Integer[] dayWeek = new Integer[8];

        for(SmokingData data : dataList){
            int d = data.getCreatedAt().getDayOfWeek().getValue();

            dayWeek[d] += 1;
        }

        return new DayOfWeekStatisticsResponse(dayWeek);
    }

}