package com.damyo.alpha.controller;

import com.damyo.alpha.dto.request.UploadPictureRequest;
import com.damyo.alpha.dto.response.PictureResponse;
import com.damyo.alpha.service.PictureService;
import com.damyo.alpha.service.S3ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RequestMapping("/pic")
@RequiredArgsConstructor
@RestController
public class PictureController {

    private final PictureService pictureService;
    private final S3ImageService s3ImageService;

    @GetMapping("/{id}")
    public ResponseEntity<PictureResponse> getPicture(@PathVariable Long id) {
        PictureResponse picture = pictureService.getPicture(id);
        return ResponseEntity.ok().body(picture);
    }

    @GetMapping("/u/{id}")
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
