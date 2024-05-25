package com.damyo.alpha.api.contest.controller;

import com.damyo.alpha.api.picture.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/contest")
@RequiredArgsConstructor
@RestController
public class ContestController {
    private PictureService pictureService;


}
