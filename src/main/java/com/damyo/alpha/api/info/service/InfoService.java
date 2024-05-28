package com.damyo.alpha.api.info.service;

import com.damyo.alpha.api.info.domain.Info;
import com.damyo.alpha.api.smokingarea.domain.SmokingArea;
import com.damyo.alpha.api.info.controller.dto.UpdateInfoRequest;
import com.damyo.alpha.api.info.controller.dto.InfoResponse;
import com.damyo.alpha.api.info.domain.InfoRepository;
import com.damyo.alpha.api.smokingarea.domain.SmokingAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InfoService {

    private final InfoRepository infoRepository;
    private final SmokingAreaRepository smokingAreaRepository;

    public void updateInfo(UpdateInfoRequest updateInfoRequest) {
        SmokingArea sa = smokingAreaRepository.findSmokingAreaById(updateInfoRequest.smokingAreaId()).get();
        infoRepository.save(updateInfoRequest.toEntity(sa));
    }

    public InfoResponse getInfo(String smokingAreaId) {
        List<Info> infos = infoRepository.findInfosBySmokingAreaId(smokingAreaId);
        Float scoreSum = 0F;
        Integer openedSum = 0;
        Integer closedSum = 0;
        Integer notExistSum = 0;
        Integer airOutSum = 0;
        Integer hygieneSum = 0;
        Integer dirtySum = 0;
        Integer indoorSum = 0;
        Integer outdoorSum = 0;
        Integer bigSum = 0;
        Integer smallSum = 0;
        Integer crowdedSum = 0;
        Integer quiteSum = 0;
        Integer chairSum = 0;
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
