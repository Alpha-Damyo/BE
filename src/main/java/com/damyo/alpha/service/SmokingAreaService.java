package com.damyo.alpha.service;

import com.damyo.alpha.dto.request.SmokingAreaListRequest;
import com.damyo.alpha.dto.request.SmokingAreaRequest;
import com.damyo.alpha.dto.response.SmokingAreaResponse;
import com.damyo.alpha.domain.SmokingArea;
import com.damyo.alpha.repository.SmokingAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SmokingAreaService {

    private final SmokingAreaRepository smokingAreaRepository;

    public SmokingAreaResponse findAreaById(String smokingAreaId) {
        SmokingArea area =  smokingAreaRepository.findSmokingAreaById(smokingAreaId);
        return area.toDTO();
    }

    public List<SmokingAreaResponse> findAreaByCreatedAt(LocalDateTime createdAt) {
        List<SmokingArea> areas = smokingAreaRepository.findAll();
        List<SmokingAreaResponse> areaResponses = new ArrayList<>();

        for(SmokingArea area : areas) {
            areaResponses.add(area.toDTO());
        }

        return areaResponses;
    }

    public List<SmokingAreaResponse> findAreaByName(String name) {
        List<SmokingArea> areas = smokingAreaRepository.findSmokingAreaByName(name);
        List<SmokingAreaResponse> areaResponses = new ArrayList<>();

        for(SmokingArea area : areas) {
            areaResponses.add(area.toDTO());
        }

        return areaResponses;
    }

    public void addSmokingArea(SmokingAreaListRequest areaListRequest) {
        for(SmokingAreaRequest area : areaListRequest.areaRequests()){
            String areaId = makeAreaIdByAddress(area.address());
            smokingAreaRepository.save(SmokingArea.builder().id(areaId).name(area.name()).latitude(area.latitude())
                    .longitude(area.longitude()).createdAt(area.createdAt()).status(true).address(area.address())
                    .description(area.description()).score(area.score()).opened(area.opened()).closed(area.closed())
                    .hygiene(area.hygiene()).dirty(area.dirty()).airOut(area.airOut()).noExist(false).indoor(area.indoor())
                    .outdoor(area.outdoor()).big(area.big()).small(area.small()).crowded(area.crowded()).quite(area.quite())
                    .chair(area.chair()).build());
        }
    }

    private String makeAreaIdByAddress(String address){
        String[] adr = address.split(" ");
        String areaName = adr[0].substring(0, 2) + "-" + adr[1].substring(0, 2);
        SmokingArea lastArea = smokingAreaRepository.findSmokingAreaIdByAreaName(areaName);

        if(lastArea.getId() == null){
            return areaName + "-0001";
        }

        Integer number = Integer.parseInt(lastArea.getId().substring(6, 10)) + 1;
        String areaNumber = String.format("%04d", number);

        return areaName + "-" + areaNumber;
    }
}
