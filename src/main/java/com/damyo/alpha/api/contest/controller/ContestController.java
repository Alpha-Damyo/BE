package com.damyo.alpha.api.contest.controller;

import com.damyo.alpha.api.picture.controller.dto.PictureSliceResponse;
import com.damyo.alpha.api.picture.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/contest")
@RequiredArgsConstructor
@RestController
public class ContestController {
    private PictureService pictureService;

    @GetMapping("/page")
    public ResponseEntity<PictureSliceResponse> getPageContestPicture(@RequestParam Long cursorId, @RequestParam String sortBy, @RequestParam String region) {
        PictureSliceResponse contestResponse = pictureService.getPageContestPicture(cursorId, sortBy, region);
        return ResponseEntity
                .ok(contestResponse);
    }

}
