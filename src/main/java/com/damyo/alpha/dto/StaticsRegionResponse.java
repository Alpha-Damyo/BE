package com.damyo.alpha.dto;

import com.damyo.alpha.domain.SmokingArea;

import java.util.List;

public record StaticsRegionResponse(
        String areaName,
        Long totalSmokingCount,
        List<SmokingArea> areaList,
        List<Long> areaSmokingCount

) {
}
