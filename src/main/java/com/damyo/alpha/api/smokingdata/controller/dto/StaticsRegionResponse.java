package com.damyo.alpha.api.smokingdata.controller.dto;

import com.damyo.alpha.api.smokingarea.domain.SmokingArea;

import java.util.List;

public record StaticsRegionResponse(
        String areaName,
        Long totalSmokingCount,
        List<SmokingArea> areaList,
        List<Long> areaSmokingCount

) {
}
