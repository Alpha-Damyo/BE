package com.damyo.alpha.api.info.controller;

import com.damyo.alpha.api.info.controller.dto.UpdateInfoRequest;
import com.damyo.alpha.api.info.controller.dto.InfoResponse;
import com.damyo.alpha.api.info.service.InfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/info")
@Tag(name = "InfoController")
public class InfoController {
    private final InfoService infoService;
    @PostMapping("/postInfo")
    @Operation(summary="리뷰 작성하기", description = "리뷰를 작성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리뷰 작성에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<?> postInfo(
            @Parameter(description = "리뷰 작성 요청사항", in = ParameterIn.DEFAULT, required = true)
            @RequestBody UpdateInfoRequest updateInfoRequest) {
        infoService.updateInfo(updateInfoRequest);
        return ResponseEntity.ok().body("정보 변경 완료");
    }

    @GetMapping("/{saId}")
    @Operation(summary="해당 흡연구역의 태그 반환", description = "해당 흡연구역의 태그를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "태그값 반환에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    public InfoResponse getInfo(
            @Parameter(description = "흡연구역 ID", in = ParameterIn.PATH, required = true)
            @PathVariable String saId) {
        return infoService.getInfo(saId);
    }
}
