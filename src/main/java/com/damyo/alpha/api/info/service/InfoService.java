package com.damyo.alpha.api.info.service;

import com.damyo.alpha.api.auth.domain.UserDetailsImpl;
import com.damyo.alpha.api.info.domain.Info;
import com.damyo.alpha.api.smokingarea.domain.SmokingArea;
import com.damyo.alpha.api.info.controller.dto.UpdateInfoRequest;
import com.damyo.alpha.api.info.controller.dto.InfoResponse;
import com.damyo.alpha.api.info.domain.InfoRepository;
import com.damyo.alpha.api.smokingarea.domain.SmokingAreaRepository;
import com.damyo.alpha.api.smokingarea.exception.AreaErrorCode;
import com.damyo.alpha.api.smokingarea.exception.AreaException;
import com.damyo.alpha.api.user.domain.User;
import com.damyo.alpha.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.damyo.alpha.api.smokingarea.exception.AreaErrorCode.NOT_FOUND_ID;

@Slf4j
@RequiredArgsConstructor
@Service
public class InfoService {

    private final InfoRepository infoRepository;
    private final SmokingAreaRepository smokingAreaRepository;
    private final UserService userService;

    private static final int POST_INFO_CONTRIBUTION_INCREMENT = 5;

    @CacheEvict(value={"areaDetailsCache", "areaSummaryCache"}, key="#updateInfoRequest.smokingAreaId()", cacheManager="contentCacheManager")
    public void updateInfo(UpdateInfoRequest updateInfoRequest, UserDetailsImpl details) {
        SmokingArea sa = smokingAreaRepository.findSmokingAreaById(updateInfoRequest.smokingAreaId())
                .orElseThrow(() -> {
                    log.error("[Info]: area not found by id | {}", updateInfoRequest.smokingAreaId());
                    return new AreaException(NOT_FOUND_ID);
                });
        User user = details.getUser();
        infoRepository.save(updateInfoRequest.toEntity(sa, user));
        int size = infoRepository.findInfosBySmokingAreaId(updateInfoRequest.smokingAreaId()).size();
        log.info("[test]: {}", size);
        Float score = (sa.getScore() * size + updateInfoRequest.score()) / (size + 1);
        smokingAreaRepository.updateSmokingAreaScoreById(score, sa.getId());
        userService.updateContribution(user.getId(), POST_INFO_CONTRIBUTION_INCREMENT);
        log.info("[Info]: info update complete");
    }

    public InfoResponse getInfo(String smokingAreaId) {
        List<Info> infos = infoRepository.findInfosBySmokingAreaId(smokingAreaId);
        Long scoreSum = 0L;
        Long sizeCnt = 0L;
        for (Info info : infos){
            scoreSum += info.getScore();
            sizeCnt += 1;
        }
        log.info("[Info]: info find complete");
        return new InfoResponse(sizeCnt, scoreSum);
    }
}
