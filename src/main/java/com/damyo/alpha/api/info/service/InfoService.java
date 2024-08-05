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
import org.springframework.stereotype.Service;

import java.util.List;

import static com.damyo.alpha.api.smokingarea.exception.AreaErrorCode.NOT_FOUND_ID;

@RequiredArgsConstructor
@Service
public class InfoService {

    private final InfoRepository infoRepository;
    private final SmokingAreaRepository smokingAreaRepository;
    private final UserService userService;

    private static final int POST_INFO_CONTRIBUTION_INCREMENT = 5;

    public void updateInfo(UpdateInfoRequest updateInfoRequest, UserDetailsImpl details) {
        SmokingArea sa = smokingAreaRepository.findSmokingAreaById(updateInfoRequest.smokingAreaId()).orElseThrow(
                () -> new AreaException(NOT_FOUND_ID)
        );
        User user = details.getUser();
        infoRepository.save(updateInfoRequest.toEntity(sa, user));
        userService.updateContribution(user.getId(), POST_INFO_CONTRIBUTION_INCREMENT);
    }

    public InfoResponse getInfo(String smokingAreaId) {
        List<Info> infos = infoRepository.findInfosBySmokingAreaId(smokingAreaId);
        Float scoreSum = 0F;
        Long openedSum = 0L;
        Long closedSum = 0L;
        Long indoorSum = 0L;
        Long outdoorSum = 0L;

        for (Info info : infos){
            scoreSum += info.getScore();
            openedSum += info.getOpened()? 1 : 0;
            closedSum += info.getClosed()? 1 : 0;
            indoorSum += info.getIndoor()? 1 : 0;
            outdoorSum += info.getOutdoor()? 1 : 0;
        }
        return new InfoResponse(infos.size(), (Math.round(scoreSum / infos.size() * 10) / 10.0F), openedSum, closedSum, indoorSum, outdoorSum);
    }
}
