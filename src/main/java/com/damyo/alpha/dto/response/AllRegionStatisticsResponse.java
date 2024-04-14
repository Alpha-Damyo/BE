package com.damyo.alpha.dto.response;

import java.util.List;
import java.util.Map;

public record AllRegionStatisticsResponse(
    List<Map.Entry<String, Integer>> allRegion
) {
}
