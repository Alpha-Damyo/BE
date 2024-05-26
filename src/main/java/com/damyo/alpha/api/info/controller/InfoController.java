package com.damyo.alpha.api.info.controller;

import com.damyo.alpha.api.info.controller.dto.UpdateInfoRequest;
import com.damyo.alpha.api.info.controller.dto.InfoResponse;
import com.damyo.alpha.api.info.service.InfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/info")
@Tag(name = "InfoController")
public class InfoController {
    private final InfoService infoService;
    @PostMapping("/postInfo")
    public ResponseEntity<?> postInfo(UpdateInfoRequest updateInfoRequest) {
        infoService.updateInfo(updateInfoRequest);
        return ResponseEntity.ok().body("정보 변경 완료");
    }

    @GetMapping("/{saId}")
    public InfoResponse getInfo(@PathVariable String saId) {
        return infoService.getInfo(saId);
    }
}
