package com.damyo.alpha.controller;

import com.damyo.alpha.dto.response.PictureResponse;
import com.damyo.alpha.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/pic")
@RequiredArgsConstructor
@RestController
public class PictureController {

    private final PictureService pictureService;

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
}
