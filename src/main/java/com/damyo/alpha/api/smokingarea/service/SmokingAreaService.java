package com.damyo.alpha.api.smokingarea.service;

import com.damyo.alpha.api.smokingarea.controller.dto.*;
import com.damyo.alpha.api.smokingarea.domain.*;
import com.damyo.alpha.api.smokingarea.exception.AreaException;
import com.damyo.alpha.api.user.controller.dto.ReportRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.parameters.P;
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
    private final SmokingAreaReportDetailRepository smokingAreaReportDetailRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String NOT_EXIST_KEY = "not-exist";
    private static final String TAG_KEY = "incorrect-tag";
    private static final String LOCATE_KEY = "incorrect-locate";
    private static final String WORD_KEY = "inappropriate-word";
    private static final String PICTURE_KEY = "inappropriate-picture";

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

    public void handleReport(String smokingAreaId, UUID userId, ReportRequest reportRequest) {
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        String setKey = "report-users::" + smokingAreaId + "::set";
        if (setOperations.isMember(setKey, userId)) {
            log.error("[Area]: area report already {}", smokingAreaId);
            throw new AreaException(ALREADY_REPORT);
        }
        log.info("[Area]: area report complete");
        setOperations.add(setKey, userId);

        String hashKey = "smoking-area::" + smokingAreaId + "::hash";
        HashOperations<String, String, Long> hashOperations = redisTemplate.opsForHash();
        if (reportRequest.notExist()) {
            hashOperations.increment(hashKey, NOT_EXIST_KEY, 1L);
        }
        else {
            hashOperations.increment(hashKey, NOT_EXIST_KEY, 0L);
        }
        if (reportRequest.incorrectTag()) {
            hashOperations.increment(hashKey, TAG_KEY, 1L);
        }
        else {
            hashOperations.increment(hashKey, TAG_KEY, 0L);
        }
        if (reportRequest.incorrectLocation()) {
            hashOperations.increment(hashKey, LOCATE_KEY, 1L);
        }
        else {
            hashOperations.increment(hashKey, LOCATE_KEY, 0L);
        }
        if (reportRequest.inappropriateWord()) {
            hashOperations.increment(hashKey, WORD_KEY, 1L);
        }
        else {
            hashOperations.increment(hashKey, WORD_KEY, 0L);
        }
        if (reportRequest.inappropriatePicture()) {
            hashOperations.increment(hashKey, PICTURE_KEY, 1L);
        }
        else {
            hashOperations.increment(hashKey, PICTURE_KEY, 0L);
        }

        if (reportRequest.otherSuggestions() != null) {
            SmokingArea smokingArea = smokingAreaRepository.findSmokingAreaById(smokingAreaId).orElseThrow(
                    () -> new AreaException(NOT_FOUND_ID)
            );
            SmokingAreaReportDetail smokingAreaReportDetail = SmokingAreaReportDetail.builder()
                            .otherSuggestion(reportRequest.otherSuggestions())
                            .smokingArea(smokingArea)
                            .build();

            smokingAreaReportDetailRepository.save(smokingAreaReportDetail);
        }
    }

    @Scheduled(cron = "0 0 0 1 * ?", zone = "Asia/Seoul")
    public void updateSmokingAreaByReport() {
//        Set<String> keys = redisTemplate.keys("SA*");
//        if (keys != null) {
//            SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
//            for (String key : keys) {
//                String smokingAreaId = key.split("::")[1];
//                if (setOperations.size(key) >= 5) {
//                    smokingAreaRepository.updateSmokingAreaActiveById(smokingAreaId);
//                    redisTemplate.delete(key);
//                }
//            }
//        }
        Set<String> keys = redisTemplate.keys("smoking-area*");
        if (keys != null) {
            HashOperations<String, String, Long> hashOperations = redisTemplate.opsForHash();
            for (String key : keys) {
                String smokingAreaId = key.split("::")[1];
                long notExist = Long.parseLong(String.valueOf(hashOperations.get(key, NOT_EXIST_KEY)));
                long incorrectTag = Long.parseLong(String.valueOf(hashOperations.get(key, TAG_KEY)));
                long incorrectLocate = Long.parseLong(String.valueOf(hashOperations.get(key, LOCATE_KEY)));
                long inappropriateWord = Long.parseLong(String.valueOf(hashOperations.get(key, WORD_KEY)));
                long inappropriatePicture = Long.parseLong(String.valueOf(hashOperations.get(key, PICTURE_KEY)));

                smokingAreaRepository.updateNotExist(smokingAreaId, notExist);
                smokingAreaRepository.updateIncorrectTag(smokingAreaId, incorrectTag);
                smokingAreaRepository.updateIncorrectLocate(smokingAreaId, incorrectLocate);
                smokingAreaRepository.updateInappropriateWord(smokingAreaId, inappropriateWord);
                smokingAreaRepository.updateInappropriatePicture(smokingAreaId, inappropriatePicture);

                if (notExist >= 5) {
                    smokingAreaRepository.updateSmokingAreaActiveById(smokingAreaId);
                    redisTemplate.delete(key);
                }
            }
        }
        log.info("[Area]: area active update complete");
    }
}
