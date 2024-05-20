package com.damyo.alpha.api.smokingdata.controller.dto;

import java.util.List;
import java.util.Map;

public record AllRegionStatisticsResponse(
    List<Map.Entry<String, Integer>> allRegion
) {
}
