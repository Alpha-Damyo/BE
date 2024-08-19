package com.damyo.alpha.api.smokingarea.service;

import com.damyo.alpha.api.smokingarea.controller.dto.*;
import com.damyo.alpha.api.smokingarea.domain.SmokingArea;
import com.damyo.alpha.api.smokingarea.domain.SmokingAreaRepository;
import com.damyo.alpha.api.smokingarea.exception.AreaException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static com.damyo.alpha.api.smokingarea.exception.AreaErrorCode.ALREADY_REPORT;
import static com.damyo.alpha.api.smokingarea.exception.AreaErrorCode.NOT_FOUND_ID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmokingAreaService {

    private final SmokingAreaRepository smokingAreaRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public List<SmokingAreaSummaryResponse> findAreaAll(){
        List<SmokingArea> areas = smokingAreaRepository.findAll();
        List<SmokingAreaSummaryResponse> areaResponses = new ArrayList<>();

        for(SmokingArea area : areas) {
            areaResponses.add(area.toSUM());
        }
        log.info("[Area]: all area find complete");
        return areaResponses;
    }

    @Cacheable(value="areaDetailsCache", key="#smokingAreaId", cacheManager="contentCacheManager")
    public SmokingAreaDetailResponse findAreaDTOById(String smokingAreaId) {
        SmokingArea area =  smokingAreaRepository.findSmokingAreaById(smokingAreaId)
                .orElseThrow(() -> {
                    log.error("[Area]: area detail not found by id | {}", smokingAreaId);
                    return new AreaException(NOT_FOUND_ID);
                });
        return area.toDTO();
    }

    @Cacheable(value="areaSummaryCache", key="#smokingAreaId", cacheManager="contentCacheManager")
    public SmokingAreaSummaryResponse findAreaSUMById(String smokingAreaId) {
        SmokingArea area =  smokingAreaRepository.findSmokingAreaById(smokingAreaId)
                .orElseThrow(() -> {
                    log.error("[Area]: area summary not found by id | {}", smokingAreaId);
                    return new AreaException(NOT_FOUND_ID);
                });
        return area.toSUM();
    }

    public List<SmokingAreaSummaryResponse> findAreaByCreatedAt(LocalDateTime createdAt) {
        List<SmokingArea> areas = smokingAreaRepository.findSmokingAreaByCreatedAt(createdAt);
        List<SmokingAreaSummaryResponse> areaResponses = new ArrayList<>();

        for(SmokingArea area : areas) {
            areaResponses.add(area.toSUM());
        }
        log.info("[Area]: area find by date complete");
        return areaResponses;
    }

//    public List<SmokingAreaSummaryResponse> findAreaByName(String name) {
//        List<SmokingArea> areas = smokingAreaRepository.findSmokingAreaByName(name);
//        List<SmokingAreaSummaryResponse> areaResponses = new ArrayList<>();
//
//        for(SmokingArea area : areas) {
//            areaResponses.add(area.toSUM());
//        }
//
//        return areaResponses;
//    }

    public SmokingArea addSmokingArea(SmokingAreaRequest area) {
        LocalDateTime current = LocalDateTime.now();

        String areaId = makeAreaIdByAddress(area.address());
        SmokingArea smokingArea =  smokingAreaRepository.save(SmokingArea.builder().id(areaId).name(area.name()).latitude(area.latitude())
                .longitude(area.longitude()).createdAt(current).status(true).address(area.address())
                .description(area.description()).score(area.score()).opened(area.opened()).closed(area.closed())
                .indoor(area.indoor()).outdoor(area.outdoor()).isActive(true).build());
        log.info("[Area]: area create complete");
        return smokingArea;
    }

    private String makeAreaIdByAddress(String address){
        String[] adr = address.split(" ");
        String areaName = adr[0].substring(0, 2) + "-" + adr[1].substring(0, 2);
        StringBuffer tmp = new StringBuffer();
        tmp.append(areaName);

        smokingAreaRepository.findSmokingAreaIdByAreaName(areaName)
                .ifPresentOrElse(
                        v ->  tmp.append(String.format("-%04d", Integer.parseInt(v.getId().substring(6, 10)) + 1)),
                        () -> tmp.append("-0001")
                );
        log.info("[Area]: areaId create complete");
        return tmp.toString();
    }

    public List<SmokingAreaSummaryResponse> findAreaByCoordinate(SearchLocateRequest request) {
        BigDecimal minLatitude, maxLatitude, minLongitude, maxLongitude;

        minLatitude = request.latitude().subtract(request.range());
        maxLatitude = request.latitude().add(request.range());
        minLongitude = request.longitude().subtract(request.range());
        maxLongitude = request.longitude().add(request.range());

        List<SmokingArea> areaList = smokingAreaRepository.findSmokingAreaByCoordinate(minLatitude, maxLatitude,
                minLongitude, maxLongitude, request.status(), request.opened(), request.closed(),
                request.indoor(), request.outdoor());

        List<SmokingAreaSummaryResponse> areaResponseList = new ArrayList<>();

        for(SmokingArea area : areaList){
            areaResponseList.add(area.toSUM());
        }
        log.info("[Area]: area search by coordinate complete");
        return areaResponseList;
    }

    public List<SmokingAreaSummaryResponse> findAreaByRegion(String region) {
        List<SmokingArea> areaList = smokingAreaRepository.findSmokingAreaByRegion(region);
        List<SmokingAreaSummaryResponse> areaResponseList = new ArrayList<>();

        for(SmokingArea area : areaList){
            areaResponseList.add(area.toSUM());
        }
        log.info("[Area]: area search by region complete");
        return areaResponseList;
    }

    public List<SmokingAreaSummaryResponse> findAreaByQuery(SearchQueryRequest query) {
        List<SmokingArea> areaList = smokingAreaRepository.findSmokingAreaByQuery(
                query.word(),
                query.status(),
                query.opened(),
                query.closed(),
                query.indoor(),
                query.outdoor());
        List<SmokingAreaSummaryResponse> areaResponseList = new ArrayList<>();

        for(SmokingArea area : areaList){
            areaResponseList.add(area.toSUM());
        }
        log.info("[Area]: area search by query complete");
        return areaResponseList;
    }

    public void reportSmokingArea(String smokingAreaId, UUID userId) {
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        String key = "SA::" + smokingAreaId;
        if (Boolean.TRUE.equals(setOperations.isMember(key, userId))) {
            log.error("[Area]: area report already {}", smokingAreaId);
            throw new AreaException(ALREADY_REPORT);
        }
        log.info("[Area]: area report complete");
        setOperations.add(key, userId);
    }


    @Scheduled(cron = "0 0 0 1 * ?", zone = "Asia/Seoul")
    public void updateSmokingAreaByReport() {
        Set<String> keys = redisTemplate.keys("SA*");
        if (keys != null) {
            SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
            for (String key : keys) {
                String smokingAreaId = key.split("::")[1];
                if (setOperations.size(key) >= 5) {
                    smokingAreaRepository.updateSmokingAreaActiveById(smokingAreaId);
                    redisTemplate.delete(key);
                }
            }
        }
        log.info("[Area]: area active update complete");
    }
}
