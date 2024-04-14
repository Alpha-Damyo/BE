package com.damyo.alpha.dto.response;

public record StatisticsDateResponse(
        HourlyStatisticsResponse hourlyStatisticsResponse,
        DailyStatisticsResponse dailyStatisticsResponse,
        WeeklyStatisticsResponse weeklyStatisticsResponse,
        MonthlyStatisticsResponse monthlyStatisticsResponse,
        DayOfWeekStatisticsResponse dayOfWeekStatisticsResponse
) {
}
