package com.damyo.alpha.api.star.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "즐겨찾기 등록시 요청으로 오는 DTO")
public record AddStarRequest (
        @Schema(description = "해당 즐겨찾기의 흡연구역 id(pk)값", example = "서울-성북-0001")
        String saId,
        @Schema(description = "사용자가 직접 입력한 즐겨찾기의 이름", example = "내집 앞")
        String customName,
        @Schema(description = "사용자가 지정한 즐겨찾기가 속한 그룹 이름", example = "회사 주변")
        String groupName
) {
}
