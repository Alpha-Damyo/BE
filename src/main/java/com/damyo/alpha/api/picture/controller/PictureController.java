package com.damyo.alpha.api.picture.controller;

import com.damyo.alpha.api.picture.controller.dto.UploadPictureRequest;
import com.damyo.alpha.api.picture.controller.dto.PictureResponse;
import com.damyo.alpha.api.picture.service.PictureService;
import com.damyo.alpha.api.picture.service.S3ImageService;
import com.damyo.alpha.api.star.controller.dto.StarResponse;
import com.damyo.alpha.global.exception.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequestMapping("/api/pic")
@RequiredArgsConstructor
@RestController
@Tag(name = "PictureController")
public class PictureController {

    private final PictureService pictureService;
    private final S3ImageService s3ImageService;

    @GetMapping("/single/{id}")
    @Operation(summary="사진 정보 반환", description = "해당 ID의 사진 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사진 정보 반환에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<PictureResponse> getPicture(
            @Parameter(description = "사진 ID", in = ParameterIn.PATH)
            @PathVariable Long id) {
        log.info("[Picture]: /single/{}", id);
        PictureResponse picture = pictureService.getPicture(id);
        return ResponseEntity.ok().body(picture);
    }

    @GetMapping("/user/{id}")
    @Operation(summary="사진 정보 반환", description = "해당 유저 ID의 사진 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사진 정보 반환에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<List<PictureResponse>> getPicturesByUser(
            @Parameter(description = "유저 ID", in = ParameterIn.PATH)
            @PathVariable UUID id) {
        log.info("[Picture]: /user/{}", id);
        List<PictureResponse> pictureList = pictureService.getPicturesByUser(id);
        return ResponseEntity.ok().body(pictureList);
    }

    @GetMapping("/sa/{id}")
    @Operation(summary="사진 정보 반환", description = "해당 흡연구역 ID의 사진 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사진 정보 반환에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<List<PictureResponse>> getPicturesBySmokingArea(
            @Parameter(description = "흡연구역 ID", in = ParameterIn.PATH)
            @PathVariable String id,
            @Parameter(description = "반환할 사진 수", in = ParameterIn.DEFAULT)
            @RequestParam Long count) {
        log.info("[Picture]: /sa/{}", id);
        List<PictureResponse> pictureList = pictureService.getPicturesBySmokingArea(id, count);
        return ResponseEntity.ok().body(pictureList);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary="사진 업로드", description = "사진을 업로드 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사진 업로드에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<?> Upload(
            @Parameter(description = "업로드할 사진의 요청 사항", in = ParameterIn.DEFAULT)
            @RequestPart(value = "dto") UploadPictureRequest dto,
            @Parameter(description = "업로드할 사진", in = ParameterIn.DEFAULT)
            @RequestPart(value = "image") MultipartFile image){
        log.info("[Picture]: /upload | {}", dto);
        String url = s3ImageService.upload(image);
        pictureService.uploadPicture(dto.userId(), dto.smokingAreaId(), url);

        return ResponseEntity.ok(url);
    }

    @Operation(summary="사진 삭제", description = "사진을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사진 삭제에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/delete")
    public ResponseEntity<?> s3delete(
            @Parameter(description = "삭제할 사진 주소", in = ParameterIn.DEFAULT)
            @RequestParam String addr){
        log.info("[Picture]: /delete | {}", addr);
        s3ImageService.deleteImageFromS3(addr);
        return ResponseEntity.ok(null);
    }
}
