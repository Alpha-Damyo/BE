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
        for (Info info : infos){
            scoreSum += info.getScore();
            openedSum += info.isOpened()? 1 : 0;
            closedSum += info.isClosed()? 1 : 0;
            notExistSum += info.isNotExist()? 1 : 0;
            airOutSum += info.isAirOut()? 1 : 0;
            hygieneSum += info.isHygiene()? 1 : 0;
            dirtySum += info.isDirty()? 1 : 0;
        }
        return new InfoResponse((float)(Math.round(scoreSum / infos.size() * 10) / 10.0), openedSum, closedSum, notExistSum, airOutSum, hygieneSum, dirtySum);
    }
}
