package com.damyo.alpha.api.smokingarea.service;

import com.damyo.alpha.api.smokingarea.controller.dto.*;
import com.damyo.alpha.api.info.controller.dto.InfoResponse;
import com.damyo.alpha.api.smokingarea.domain.SmokingArea;
import com.damyo.alpha.api.smokingarea.domain.SmokingAreaRepository;
import com.damyo.alpha.api.info.service.InfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SmokingAreaService {

    private final SmokingAreaRepository smokingAreaRepository;
    private final InfoService infoService;

    public List<SmokingAreaResponse> findAreaAll(){
        List<SmokingArea> areas = smokingAreaRepository.findAll();
        List<SmokingAreaResponse> areaResponses = new ArrayList<>();

        for(SmokingArea area : areas) {
            areaResponses.add(area.toDTO());
        }

        return areaResponses;
    }

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

    public List<SmokingAreaResponse> findAreaByCoordinate(SearchLocateRequest request) {
        BigDecimal minLatitude, maxLatitude, minLongitude, maxLongitude;

        minLatitude = request.latitude().subtract(request.range());
        maxLatitude = request.latitude().add(request.range());
        minLongitude = request.longitude().subtract(request.range());
        maxLongitude = request.longitude().add(request.range());

        List<SmokingArea> areaList = smokingAreaRepository.findSmokingAreaByCoordinate(minLatitude, maxLatitude,
                minLongitude, maxLongitude, request.status(), request.opened(), request.closed(),
                request.indoor(), request.outdoor(), request.hygeine(), request.chair());

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
                query.airOut(),
                query.indoor(),
                query.outdoor(),
                query.big(),
                query.small(),
                query.crowded(),
                query.quite(),
                query.chair());
        List<SmokingAreaResponse> areaResponseList = new ArrayList<>();

        for(SmokingArea area : areaList){
            areaResponseList.add(area.toDTO());
        }
        return areaResponseList;
    }

    public void updateAllArea(){
        List<SmokingArea> areas = smokingAreaRepository.findAll();
        for(SmokingArea area : areas){
            String id = area.getId();
            updateAreaByInfo(id);
        }
    }

    private void updateAreaByInfo(String id){
        InfoResponse infoResponse = infoService.getInfo(id);

        boolean isOpened;
        if(infoResponse.opened() >= infoResponse.closed()){
            isOpened = true;
        }
        else{
            isOpened = false;
        }

        boolean isClean;
        if(infoResponse.hygiene() >= infoResponse.dirty()){
            isClean = true;
        }
        else{
            isClean = false;
        }

        boolean isOut;
        if(infoResponse.outdoor() >= infoResponse.indoor()){
            isOut = true;
        }
        else{
            isOut = false;
        }

        boolean isBig;
        if(infoResponse.big() >= infoResponse.small()){
            isBig = true;
        }
        else{
            isBig = false;
        }

        boolean isQuite;
        if(infoResponse.quite() >= infoResponse.crowded()){
            isQuite = true;
        }
        else{
            isQuite = false;
        }

        boolean isAir;
        if(infoResponse.airOut() >= infoResponse.size() - infoResponse.airOut()){
            isAir = true;
        }
        else{
            isAir = false;
        }

        boolean isChair;
        if(infoResponse.chair() >= infoResponse.size() - infoResponse.chair()){
            isChair = true;
        }
        else{
            isChair = false;
        }

        boolean noExist;
        if(infoResponse.notExist() > 10){
            noExist = true;
        }
        else{
            noExist = false;
        }

        smokingAreaRepository.updateSmokingAreaInfoById(isOpened, isOpened,
                                                        isClean, isClean,
                                                        isAir , infoResponse.score(),
                                                        isOut, isOut,
                                                        isBig, isBig,
                                                        isQuite, isQuite,
                                                        isChair, noExist, id);

    }
}
