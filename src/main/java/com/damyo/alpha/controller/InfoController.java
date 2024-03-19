package com.damyo.alpha.controller;

import com.damyo.alpha.dto.request.UpdateInfoRequest;
import com.damyo.alpha.dto.response.InfoResponse;
import com.damyo.alpha.service.InfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class InfoController {
    private final InfoService infoService;
    @PostMapping("/info/postInfo")
    public void postInfo(UpdateInfoRequest updateInfoRequest) {
        infoService.updateInfo(updateInfoRequest);
    }

    @PostMapping("/info/getInfo")
    public InfoResponse getInfo(@RequestParam String saId) {
        return infoService.getInfo(saId);
    }
}
