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
        userService.updateContribution(user.getId(), POST_INFO_CONTRIBUTION_INCREMENT);
    }

    public InfoResponse getInfo(String smokingAreaId) {
        List<Info> infos = infoRepository.findInfosBySmokingAreaId(smokingAreaId);
        Float scoreSum = 0F;
        Long openedSum = 0L;
        Long closedSum = 0L;
        Long notExistSum = 0L;
        Long airOutSum = 0L;
        Long hygieneSum = 0L;
        Long dirtySum = 0L;
        Long indoorSum = 0L;
        Long outdoorSum = 0L;
        Long bigSum = 0L;
        Long smallSum = 0L;
        Long crowdedSum = 0L;
        Long quiteSum = 0L;
        Long chairSum = 0L;
        for (Info info : infos){
            scoreSum += info.getScore();
            openedSum += info.getOpened()? 1 : 0;
            closedSum += info.getClosed()? 1 : 0;
            notExistSum += info.getNotExist()? 1 : 0;
            airOutSum += info.getAirOut()? 1 : 0;
            hygieneSum += info.getHygiene()? 1 : 0;
            dirtySum += info.getDirty()? 1 : 0;
            indoorSum += info.getIndoor()? 1 : 0;
            outdoorSum += info.getOutdoor()? 1 : 0;
            bigSum += info.getBig()? 1 : 0;
            smallSum += info.getSmall()? 1 : 0;
            crowdedSum += info.getCrowded()? 1 : 0;
            quiteSum += info.getQuite()? 1 : 0;
            chairSum += info.getChair()? 1 : 0;
        }
        return new InfoResponse(infos.size(), (Math.round(scoreSum / infos.size() * 10) / 10.0F), openedSum, closedSum, notExistSum, airOutSum, hygieneSum, dirtySum, indoorSum, outdoorSum, bigSum, smallSum, crowdedSum, quiteSum, chairSum);
    }
}
