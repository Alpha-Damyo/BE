package com.damyo.alpha.api.info.controller;

import com.damyo.alpha.api.auth.domain.UserDetailsImpl;
import com.damyo.alpha.api.info.controller.dto.UpdateInfoRequest;
import com.damyo.alpha.api.info.controller.dto.InfoResponse;
import com.damyo.alpha.api.info.domain.Info;
import com.damyo.alpha.api.info.service.InfoService;
import com.damyo.alpha.api.picture.service.PictureService;
import com.damyo.alpha.api.picture.service.S3ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/info")
@Tag(name = "InfoController")
public class InfoController {
    private final InfoService infoService;
    private final PictureService pictureService;
    private final S3ImageService s3ImageService;

    @PostMapping(value="/postInfo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary="리뷰 작성하기", description = "리뷰를 작성한다.", security = @SecurityRequirement(name = "jwt"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리뷰 작성에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<?> postInfo(
            @AuthenticationPrincipal UserDetailsImpl details,
            @Parameter(description = "리뷰 작성 요청사항", in = ParameterIn.DEFAULT, required = true)
            @RequestPart UpdateInfoRequest updateInfoRequest,
            @Parameter(description = "리뷰 첨부 사진", in = ParameterIn.DEFAULT)
            @RequestPart(required = false) MultipartFile imgFile
            ) {
        log.info("[Info]: /postInfo | {}", updateInfoRequest);
        infoService.updateInfo(updateInfoRequest, details);

        if(imgFile != null) {
            String imgUrl = s3ImageService.upload(imgFile);
            pictureService.uploadPicture(details.getId(), updateInfoRequest.smokingAreaId(), imgUrl);
        }

        return ResponseEntity.ok().body("리뷰 작성 완료");
    }

    @GetMapping("/{saId}")
    @Operation(summary="해당 흡연구역의 태그 반환", description = "해당 흡연구역의 태그를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "태그값 반환에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    public InfoResponse getInfo(
            @Parameter(description = "흡연구역 ID", in = ParameterIn.PATH, required = true)
            @PathVariable String saId) {
        log.info("[Info]: /saId/{}", saId);
        return infoService.getInfo(saId);
    }
}
