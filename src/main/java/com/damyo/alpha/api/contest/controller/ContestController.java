package com.damyo.alpha.api.contest.controller;

import com.damyo.alpha.api.picture.controller.dto.PictureSliceResponse;
import com.damyo.alpha.api.picture.service.PictureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/contest")
@RequiredArgsConstructor
@RestController
@Tag(name = "ContestController")
public class ContestController {
    private PictureService pictureService;

    @GetMapping("/page")
    @Operation(summary="사진 콘테스트의 사진 반환", description = "페이지네이션을 적용한 사진 URL 리스트가 반환됩니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사진 URL 리스트 반환에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<PictureSliceResponse> getPageContestPicture(
            @Parameter(description = "페이지네이션 위치를 위한 커서", in = ParameterIn.DEFAULT, required = true)
            @RequestParam Long cursorId,
            @Parameter(description = "정렬 기준", in = ParameterIn.DEFAULT)
            @RequestParam(required = false) String sortBy,
            @Parameter(description = "검색 지역", in = ParameterIn.DEFAULT)
            @RequestParam(required = false) String region) {
        PictureSliceResponse contestResponse = pictureService.getPageContestPicture(cursorId, sortBy, region);
        return ResponseEntity
                .ok(contestResponse);
    }

}
