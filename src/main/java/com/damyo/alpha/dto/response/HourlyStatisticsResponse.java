package com.damyo.alpha.dto.response;

public record HourlyStatisticsResponse(
        Integer hour0To3,
        Integer hour3To6,
        Integer hour6To9,
        Integer hour9To12,
        Integer hour12To15,
        Integer hour15To18,
        Integer hour18To21,
        Integer hour21To0
) {
}
