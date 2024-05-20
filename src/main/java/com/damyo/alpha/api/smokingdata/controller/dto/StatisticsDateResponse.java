package com.damyo.alpha.api.smokingdata.controller.dto;

public record StatisticsDateResponse(
        HourlyStatisticsResponse hourlyStatisticsResponse,
        DailyStatisticsResponse dailyStatisticsResponse,
        WeeklyStatisticsResponse weeklyStatisticsResponse,
        MonthlyStatisticsResponse monthlyStatisticsResponse,
        DayOfWeekStatisticsResponse dayOfWeekStatisticsResponse
) {
}
