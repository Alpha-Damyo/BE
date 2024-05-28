package com.damyo.alpha.api.smokingarea.service;

import com.damyo.alpha.api.smokingarea.controller.dto.*;
import com.damyo.alpha.api.info.controller.dto.InfoResponse;
import com.damyo.alpha.api.smokingarea.domain.SmokingArea;
import com.damyo.alpha.api.smokingarea.domain.SmokingAreaRepository;
import com.damyo.alpha.api.info.service.InfoService;
import com.damyo.alpha.api.smokingarea.exception.AreaErrorCode;
import com.damyo.alpha.api.smokingarea.exception.AreaException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
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

    public List<SmokingAreaSummaryResponse> findAreaAll(){
        List<SmokingArea> areas = smokingAreaRepository.findAll();
        List<SmokingAreaSummaryResponse> areaResponses = new ArrayList<>();

        for(SmokingArea area : areas) {
            areaResponses.add(area.toSUM());
        }

        return areaResponses;
    }

    public SmokingAreaDetailResponse findAreaById(String smokingAreaId) {
        SmokingArea area =  smokingAreaRepository.findSmokingAreaById(smokingAreaId)
                .orElseThrow(() -> new AreaException(AreaErrorCode.NOT_FOUND_ID));
        return area.toDTO();
    }

    public List<SmokingAreaSummaryResponse> findAreaByCreatedAt(LocalDateTime createdAt) {
        List<SmokingArea> areas = smokingAreaRepository.findSmokingAreaByCreatedAt(createdAt);
        List<SmokingAreaSummaryResponse> areaResponses = new ArrayList<>();

        for(SmokingArea area : areas) {
            areaResponses.add(area.toSUM());
        }

        return areaResponses;
    }

    public List<SmokingAreaSummaryResponse> findAreaByName(String name) {
        List<SmokingArea> areas = smokingAreaRepository.findSmokingAreaByName(name);
        List<SmokingAreaSummaryResponse> areaResponses = new ArrayList<>();

        for(SmokingArea area : areas) {
            areaResponses.add(area.toSUM());
        }

        return areaResponses;
    }

    public SmokingArea addSmokingArea(SmokingAreaRequest area) {
        LocalDateTime current = LocalDateTime.now();

        String areaId = makeAreaIdByAddress(area.address());
        SmokingArea smokingArea =  smokingAreaRepository.save(SmokingArea.builder().id(areaId).name(area.name()).latitude(area.latitude())
                .longitude(area.longitude()).createdAt(current).status(true).address(area.address())
                .description(area.description()).score(area.score()).opened(area.opened()).closed(area.closed())
                .hygiene(null).dirty(null).airOut(null).noExist(false).indoor(area.indoor())
                .outdoor(area.outdoor()).big(null).small(null).crowded(null).quite(null)
                .chair(null).build());

        return smokingArea;
    }

    private String makeAreaIdByAddress(String address){
        String[] adr = address.split(" ");
        String areaName = adr[0].substring(0, 2) + "-" + adr[1].substring(0, 2);
        SmokingArea lastArea = smokingAreaRepository.findSmokingAreaIdByAreaName(areaName).get();

        if(lastArea.getId() == null){
            return areaName + "-0001";
        }

        Integer number = Integer.parseInt(lastArea.getId().substring(6, 10)) + 1;
        String areaNumber = String.format("%04d", number);

        return areaName + "-" + areaNumber;
    }

    public List<SmokingAreaSummaryResponse> findAreaByCoordinate(SearchLocateRequest request) {
        BigDecimal minLatitude, maxLatitude, minLongitude, maxLongitude;

        minLatitude = request.latitude().subtract(request.range());
        maxLatitude = request.latitude().add(request.range());
        minLongitude = request.longitude().subtract(request.range());
        maxLongitude = request.longitude().add(request.range());

        List<SmokingArea> areaList = smokingAreaRepository.findSmokingAreaByCoordinate(minLatitude, maxLatitude,
                minLongitude, maxLongitude, request.status(), request.opened(), request.closed(),
                request.indoor(), request.outdoor(), request.hygiene(), request.chair());

        List<SmokingAreaSummaryResponse> areaResponseList = new ArrayList<>();

        for(SmokingArea area : areaList){
            areaResponseList.add(area.toSUM());
        }
        return areaResponseList;
    }

    public List<SmokingAreaSummaryResponse> findAreaByRegion(String region) {
        List<SmokingArea> areaList = smokingAreaRepository.findSmokingAreaByRegion(region);
        List<SmokingAreaSummaryResponse> areaResponseList = new ArrayList<>();

        for(SmokingArea area : areaList){
            areaResponseList.add(area.toSUM());
        }
        return areaResponseList;
    }

    public List<SmokingAreaSummaryResponse> findAreaByQuery(SearchQueryRequest query) {
        List<SmokingArea> areaList = smokingAreaRepository.findSmokingAreaByQuery(
                query.word(),
                query.status(),
                query.opened(),
                query.closed(),
                query.hygiene(),
                query.airOut(),
                query.indoor(),
                query.outdoor(),
                query.big(),
                query.small(),
                query.crowded(),
                query.quite(),
                query.chair());
        List<SmokingAreaSummaryResponse> areaResponseList = new ArrayList<>();

        for(SmokingArea area : areaList){
            areaResponseList.add(area.toSUM());
        }
        return areaResponseList;
    }

    @Scheduled(cron = "0 0 4 * * MON")
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
