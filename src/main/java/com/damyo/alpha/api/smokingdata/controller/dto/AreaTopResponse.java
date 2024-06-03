package com.damyo.alpha.api.smokingdata.controller.dto;

import java.util.List;
import java.util.Map;

public record AreaTopResponse(
    List<Map<String, Long>> areaTop
) {
}
