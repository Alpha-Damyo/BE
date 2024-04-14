package com.damyo.alpha.dto.response;

import com.damyo.alpha.domain.SmokingArea;

import java.util.List;

public record StatisticsRegionResponse(
        AllRegionStatisticsResponse allRegionStatisticsResponse,
        AreaTopResponse areaTopResponse
) {
}
