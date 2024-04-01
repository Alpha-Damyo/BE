package com.damyo.alpha.service;

import com.damyo.alpha.domain.Info;
import com.damyo.alpha.domain.SmokingArea;
import com.damyo.alpha.dto.request.UpdateInfoRequest;
import com.damyo.alpha.dto.response.InfoResponse;
import com.damyo.alpha.repository.InfoRepository;
import com.damyo.alpha.repository.SmokingAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InfoService {

    private final InfoRepository infoRepository;
    private final SmokingAreaRepository smokingAreaRepository;

    public void updateInfo(UpdateInfoRequest updateInfoRequest) {
        SmokingArea sa = smokingAreaRepository.findSmokingAreaById(updateInfoRequest.smokingAreaId());
        infoRepository.save(updateInfoRequest.toEntity(sa));
    }

    public InfoResponse getInfo(String smokingAreaId) {
        List<Info> infos = infoRepository.findInfosBySmokingAreaId(smokingAreaId);
        float scoreSum = 0;
        int openedSum = 0;
        int closedSum = 0;
        int notExistSum = 0;
        int airOutSum = 0;
        int hygieneSum = 0;
        int dirtySum = 0;
        int indoorSum = 0;
        int outdoorSum = 0;
        int bigSum = 0;
        int smallSum = 0;
        int crowdedSum = 0;
        int quiteSum = 0;
        int chairSum = 0;
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
        return new InfoResponse(infos.size(), (float)(Math.round(scoreSum / infos.size() * 10) / 10.0), openedSum, closedSum, notExistSum, airOutSum, hygieneSum, dirtySum, indoorSum, outdoorSum, bigSum, smallSum, crowdedSum, quiteSum, chairSum);
    }
}
