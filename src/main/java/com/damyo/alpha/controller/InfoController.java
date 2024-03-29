package com.damyo.alpha.controller;

import com.damyo.alpha.dto.request.UpdateInfoRequest;
import com.damyo.alpha.dto.response.InfoResponse;
import com.damyo.alpha.service.InfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class InfoController {
    private final InfoService infoService;
    @PostMapping("/info/postInfo")
    public ResponseEntity<?> postInfo(UpdateInfoRequest updateInfoRequest) {
        infoService.updateInfo(updateInfoRequest);
        return ResponseEntity.ok().body("정보 변경 완료");
    }

    @GetMapping("/info/{saId}")
    public InfoResponse getInfo(@PathVariable String saId) {
        return infoService.getInfo(saId);
    }
}
