package com.damyo.alpha.controller;

import com.damyo.alpha.dto.response.PictureResponse;
import com.damyo.alpha.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequestMapping("/pic")
@RequiredArgsConstructor
@RestController
public class PictureController {

    private PictureService pictureService;

    @PostMapping("/{id}")
    public ResponseEntity<PictureResponse> getPicture(@PathVariable Long id) {
        PictureResponse picture = pictureService.getPicture(id);
        return ResponseEntity.ok().body(picture);
    }

    @PostMapping("/u/{id}")
    public ResponseEntity<List<PictureResponse>> getPicturesByUser(@PathVariable UUID id) {
        List<PictureResponse> pictureList = pictureService.getPicturesByUser(id);
        return ResponseEntity.ok().body(pictureList);
    }

    @PostMapping("/sa/{id}")
    public ResponseEntity<List<PictureResponse>> getPicturesBySmokingArea(@PathVariable String id) {
        List<PictureResponse> pictureList = pictureService.getPicturesBySmokingArea(id);
        return ResponseEntity.ok().body(pictureList);
    }
}
