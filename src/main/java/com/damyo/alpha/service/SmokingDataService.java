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
        List<Object[]> list = smokingDataRepository.findAreaTopByCreatedAt(LocalDateTime.now().minusYears(1), LocalDateTime.now());

    }

    private AreaTopResponse getAreaTop() {
        List<Object[]> list = smokingDataRepository.findAreaTopByCreatedAt(LocalDateTime.now().minusYears(1), LocalDateTime.now());
        if(list.size() < 3) return new AreaTopResponse(list);
        return  new AreaTopResponse(list.subList(0, 3));
    }

    //TODO 지역 top3 통계 기능
    private RegionTopResponse getRegionTop() {

    }

    public StatisticsDateResponse getStatisticsByDate() {
        List<SmokingData> dataList = smokingDataRepository.findSmokingDataByCreateAt(LocalDateTime.now().minusYears(1), LocalDateTime.now());
        Integer peopleSum = smokingDataRepository.findUserNumberByCreateAt(LocalDateTime.now().minusYears(1), LocalDateTime.now());
        return new StatisticsDateResponse(getHourlyStatistics(dataList, peopleSum), getDailyStatistics(dataList, peopleSum), getWeeklyStatistics(dataList, peopleSum), getMonthlyStatistics(dataList, peopleSum), getDayWeekStatistics(dataList, peopleSum));
    }

    private HourlyStatisticsResponse getHourlyStatistics(List<SmokingData> dataList, Integer peopleSum) {
        Double[] time = new Double[9];

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

        if(peopleSum != 0){
            for(int i=1; i<9; i++) time[i] /= peopleSum;
        }

        return new HourlyStatisticsResponse(time);
    }

    private DailyStatisticsResponse getDailyStatistics(List<SmokingData> dataList, Integer peopleSum) {
        Double[] days = new Double[32];

        for(SmokingData data : dataList) {
            int d = data.getCreatedAt().getDayOfMonth();
            days[d] += 1;
        }

        if(peopleSum != 0){
            for(int i=1; i<32; i++) days[i] /= peopleSum;
        }

        return new DailyStatisticsResponse(days);
    }

    private  WeeklyStatisticsResponse getWeeklyStatistics(List<SmokingData> dataList, Integer peopleSum) {
        Double[] weeks = new Double[5];

        for(SmokingData data : dataList) {
            int d = data.getCreatedAt().getDayOfMonth();
            if(d <= 7) weeks[1] += 1;
            else if(d <= 15) weeks[2] += 1;
            else if(d <= 23) weeks[3] += 1;
            else weeks[4] += 1;
        }


        if(peopleSum != 0){
            for(int i=1; i<5; i++) weeks[i] /= peopleSum;
        }

        return new WeeklyStatisticsResponse(weeks);
    }

    private MonthlyStatisticsResponse getMonthlyStatistics(List<SmokingData> dataList, Integer peopleSum) {
        Double[] month = new Double[13];

        for(SmokingData data : dataList) {
            int m = data.getCreatedAt().getMonthValue();
            month[m] += 1;
        }

        if(peopleSum != 0){
            for(int i=1; i<13; i++) month[i] /= peopleSum;
        }

        return new MonthlyStatisticsResponse(month);
    }

    private DayOfWeekStatisticsResponse getDayWeekStatistics(List<SmokingData> dataList, Integer peopleSum) {
        Double[] dayWeek = new Double[8];

        for(SmokingData data : dataList){
            int d = data.getCreatedAt().getDayOfWeek().getValue();
            dayWeek[d] += 1;
        }

        if(peopleSum != 0){
            for(int i=1; i<8; i++) dayWeek[i] /= peopleSum;
        }

        return new DayOfWeekStatisticsResponse(dayWeek);
    }

}