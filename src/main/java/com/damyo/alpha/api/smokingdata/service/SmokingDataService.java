package com.damyo.alpha.api.smokingdata.service;

import com.damyo.alpha.api.smokingarea.domain.SmokingArea;
import com.damyo.alpha.api.smokingarea.domain.SmokingAreaRepository;
import com.damyo.alpha.api.smokingdata.controller.dto.*;
import com.damyo.alpha.api.smokingdata.domain.SmokingData;
import com.damyo.alpha.api.smokingdata.domain.SmokingDataRepository;
import com.damyo.alpha.api.user.domain.User;
import com.damyo.alpha.api.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SmokingDataService {

    private final SmokingDataRepository smokingDataRepository;
    private final SmokingAreaRepository smokingAreaRepository;
    private final UserRepository userRepository;

    public void addSmokingData(UUID userId, String areaId) {
        SmokingArea area = smokingAreaRepository.findSmokingAreaById(areaId).get();
        User user = userRepository.findUserById(userId).get();
        smokingDataRepository.save(SmokingData.builder().user(user).createdAt(LocalDateTime.now()).smokingArea(area).build());
    }

    public StatisticsRegionResponse getStatisticsByRegion() {
        List<Object[]> list = smokingDataRepository.findAreaTopByCreatedAt(LocalDateTime.now().minusYears(1), LocalDateTime.now());
        return new StatisticsRegionResponse(getAllRegion(list), getAreaTop(list));
    }

    private AllRegionStatisticsResponse getAllRegion(List<Object[]> list) {
        Map<String, Long> allRegion = new HashMap<>();

        for(Object[] area : list) {
            String name = ((String) area[0]).substring(0, 5);
            Long count = (Long) area[1];
            allRegion.compute(name, (k, v) -> (v == null) ? count : v + count);
        }

        List<Map.Entry<String, Long>> allRegionList = new ArrayList<>(allRegion.entrySet());

        Collections.sort(allRegionList, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        return new AllRegionStatisticsResponse(allRegionList);
    }

    private AreaTopResponse getAreaTop(List<Object[]> list) {

        List<Map<String, Long>> areaTopList = new ArrayList<>();

        for(Object[] area : list) {
            String name = (String) area[0];
            Long count =  (Long) area[1];
            areaTopList.add(Map.of(name, count));
            if(areaTopList.size() == 3) break;
        }

        return  new AreaTopResponse(areaTopList);
    }

    public StatisticsDateResponse getStatisticsByDate() {
        List<SmokingData> dataList = smokingDataRepository.findSmokingDataByCreateAt(LocalDateTime.now().minusYears(1), LocalDateTime.now());
        Integer peopleSum = smokingDataRepository.findUserNumberByCreateAt(LocalDateTime.now().minusYears(1), LocalDateTime.now());

        List<SmokingData> dataMonthList = smokingDataRepository.findSmokingDataByCreateAt(LocalDateTime.now().minusMonths(1), LocalDateTime.now());
        Integer peopleMonth = smokingDataRepository.findUserNumberByCreateAt(LocalDateTime.now().minusMonths(1), LocalDateTime.now());

        return new StatisticsDateResponse(getHourlyStatistics(dataList, peopleSum), getDailyStatistics(dataList, peopleSum), getWeeklyStatistics(dataMonthList, peopleMonth), getMonthlyStatistics(dataList, peopleSum), getDayWeekStatistics(dataList, peopleSum));
    }

    private HourlyStatisticsResponse getHourlyStatistics(List<SmokingData> dataList, Integer peopleSum) {
        double[] time = new double[9];

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
        double[] days = new double[32];

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
        double[] weeks = new double[5];

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
        double[] month = new double[13];

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
        double[] dayWeek = new double[8];

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