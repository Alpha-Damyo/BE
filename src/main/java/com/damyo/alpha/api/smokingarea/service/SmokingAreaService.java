package com.damyo.alpha.api.smokingarea.service;

import com.damyo.alpha.api.auth.domain.UserDetailsImpl;
import com.damyo.alpha.api.report.domain.Report;
import com.damyo.alpha.api.report.domain.ReportRepository;
import com.damyo.alpha.api.report.exception.ReportErrorCode;
import com.damyo.alpha.api.report.exception.ReportException;
import com.damyo.alpha.api.smokingarea.controller.dto.*;
import com.damyo.alpha.api.info.controller.dto.InfoResponse;
import com.damyo.alpha.api.smokingarea.domain.SmokingArea;
import com.damyo.alpha.api.smokingarea.domain.SmokingAreaRepository;
import com.damyo.alpha.api.info.service.InfoService;
import com.damyo.alpha.api.smokingarea.exception.AreaErrorCode;
import com.damyo.alpha.api.smokingarea.exception.AreaException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static com.damyo.alpha.api.report.exception.ReportErrorCode.ALREADY_REPORT;
import static com.damyo.alpha.api.smokingarea.exception.AreaErrorCode.NOT_FOUND_ID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmokingAreaService {

    private final SmokingAreaRepository smokingAreaRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ReportRepository reportRepository;

    public List<SmokingAreaSummaryResponse> findAreaAll(){
        List<SmokingArea> areas = smokingAreaRepository.findAll();
        List<SmokingAreaSummaryResponse> areaResponses = new ArrayList<>();

        for(SmokingArea area : areas) {
            areaResponses.add(area.toSUM());
        }

        return areaResponses;
    }

    public SmokingArea findAreaById(String smokingAreaId) {
        SmokingArea area =  smokingAreaRepository.findSmokingAreaById(smokingAreaId)
                .orElseThrow(() -> new AreaException(NOT_FOUND_ID));
        return area;
    }

    public List<SmokingAreaSummaryResponse> findAreaByCreatedAt(LocalDateTime createdAt) {
        List<SmokingArea> areas = smokingAreaRepository.findSmokingAreaByCreatedAt(createdAt);
        List<SmokingAreaSummaryResponse> areaResponses = new ArrayList<>();

        for(SmokingArea area : areas) {
            areaResponses.add(area.toSUM());
        }

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
                query.indoor(),
                query.outdoor());
        List<SmokingAreaSummaryResponse> areaResponseList = new ArrayList<>();

        for(SmokingArea area : areaList){
            areaResponseList.add(area.toSUM());
        }
        return areaResponseList;
    }

    @Transactional
    public void reportSmokingArea(String smokingAreaId, UUID userId) {
        if (reportRepository.findReportByUserIdAndSmokingAreaId(userId, smokingAreaId).isPresent()) {
            throw new ReportException(ALREADY_REPORT);
        }
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        String key = "SA::" + smokingAreaId;
        String hashKey = "report";
        if (!hashOperations.hasKey(key, hashKey)) {
            hashOperations.put(key, hashKey, 0L);
        }
        hashOperations.increment(key, hashKey, 1L);
        reportRepository.save(new Report(userId, smokingAreaId));
    }


    @Transactional
    @Scheduled(cron = "0 0 0 1 * ?", zone = "Asia/Seoul")
    public void updateSmokingAreaByReport() {
        Set<String> keys = redisTemplate.keys("SA*");
        String hashKey = "report";
        if (keys != null) {
            HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
            for (String key : keys) {
                String smokingAreaId = key.split("::")[1];
                log.info(smokingAreaId);
                if (Long.parseLong(String.valueOf(hashOperations.get(key, hashKey))) >= 5) {
                    smokingAreaRepository.updateSmokingAreaActiveById(smokingAreaId);
                    hashOperations.delete(key, hashKey);
                }
            }
        }
    }
}
