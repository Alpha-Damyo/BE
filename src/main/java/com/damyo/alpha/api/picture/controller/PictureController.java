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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/pic")
@RequiredArgsConstructor
@RestController
@Tag(name = "PictureController")
public class PictureController {

    private final PictureService pictureService;
    private final S3ImageService s3ImageService;

    @GetMapping("/single/{id}")
    public ResponseEntity<PictureResponse> getPicture(@PathVariable Long id) {
        PictureResponse picture = pictureService.getPicture(id);
        return ResponseEntity.ok().body(picture);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<PictureResponse>> getPicturesByUser(@PathVariable UUID id) {
        List<PictureResponse> pictureList = pictureService.getPicturesByUser(id);
        return ResponseEntity.ok().body(pictureList);
    }

    @GetMapping("/sa/{id}")
    public ResponseEntity<List<PictureResponse>> getPicturesBySmokingArea(@PathVariable String id) {
        List<PictureResponse> pictureList = pictureService.getPicturesBySmokingArea(id);
        return ResponseEntity.ok().body(pictureList);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> Upload(@RequestPart(value = "dto") UploadPictureRequest dto, @RequestPart(value = "image") MultipartFile image){
        String url = s3ImageService.upload(image);
        pictureService.uploadPicture(dto, url);

        return ResponseEntity.ok(url);
    }

    @GetMapping("/delete")
    public ResponseEntity<?> s3delete(@RequestParam String addr){
        s3ImageService.deleteImageFromS3(addr);
        return ResponseEntity.ok(null);
    }
}
