package com.damyo.alpha.service;

import com.damyo.alpha.dto.request.SearchQueryRequest;
import com.damyo.alpha.dto.request.SmokingAreaListRequest;
import com.damyo.alpha.dto.request.SmokingAreaRequest;
import com.damyo.alpha.dto.response.SmokingAreaResponse;
import com.damyo.alpha.domain.SmokingArea;
import com.damyo.alpha.repository.SmokingAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        List<SmokingArea> areas = smokingAreaRepository.findSmokingAreaByCreatedAt(createdAt);
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
                    .hygiene(area.hygiene()).dirty(area.dirty()).airOut(area.airOut()).noExist(false).build());
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

    public List<SmokingAreaResponse> findAreaByCoordinate(BigDecimal latitude, BigDecimal longitude, BigDecimal range) {
        BigDecimal minLatitude, maxLatitude, minLongitude, maxLongitude;
        minLatitude = latitude.subtract(range);
        maxLatitude = latitude.add(range);
        minLongitude = longitude.subtract(range);
        maxLongitude = longitude.add(range);

        List<SmokingArea> areaList = smokingAreaRepository.findSmokingAreaByCoordinate(minLatitude, maxLatitude, minLongitude, maxLongitude, range);
        List<SmokingAreaResponse> areaResponseList = new ArrayList<>();

        for(SmokingArea area : areaList){
            areaResponseList.add(area.toDTO());
        }
        return areaResponseList;
    }

    public List<SmokingAreaResponse> findAreaByRegion(String region) {
        List<SmokingArea> areaList = smokingAreaRepository.findSmokingAreaByRegion(region);
        List<SmokingAreaResponse> areaResponseList = new ArrayList<>();

        for(SmokingArea area : areaList){
            areaResponseList.add(area.toDTO());
        }
        return areaResponseList;
    }

    public List<SmokingAreaResponse> findAreaByQuery(SearchQueryRequest query) {
        List<SmokingArea> areaList = smokingAreaRepository.findSmokingAreaByQuery(
                query.word(),
                query.status(),
                query.opened(),
                query.closed(),
                query.hygeine(),
                query.airOut());
        List<SmokingAreaResponse> areaResponseList = new ArrayList<>();

        for(SmokingArea area : areaList){
            areaResponseList.add(area.toDTO());
        }
        return areaResponseList;
    }

    public void updateAreaRate(){

    }

    public void updateAreaTags(){

    }
}
