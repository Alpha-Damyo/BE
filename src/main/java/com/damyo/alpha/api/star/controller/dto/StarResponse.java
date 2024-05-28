package com.damyo.alpha.api.star.controller.dto;

import com.damyo.alpha.api.star.domain.Star;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "즐겨찾기 정보를 반환하는 DTO")
public record StarResponse(
        @Schema(description = "DB에 저장된 즐겨찾기의 id(pk)값", example = "1")
        Long id,
        @Schema(description = "해당 즐겨찾기의 흡연구역 id(pk)값", example = "")
        String saId,
        @Schema(description = "사용자가 직접 입력한 즐겨찾기의 이름", example = "내집 앞")
        String customName,
        @Schema(description = "사용자가 지정한 즐겨찾기가 속한 그룹 이름", example = "회사 주변")
        String groupName
) {
    public StarResponse(Star star) {
        this(star.getId(), star.getSmokingArea().getId(), star.getCustomName(), star.getGroupName());
    }
}
