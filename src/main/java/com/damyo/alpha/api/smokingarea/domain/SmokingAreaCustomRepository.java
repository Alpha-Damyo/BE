package com.damyo.alpha.api.smokingarea.domain;

import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface SmokingAreaCustomRepository {
    List<SmokingArea> findSmokingAreaByCoordinate(BigDecimal minLatitude, BigDecimal maxLatitude,
                                                  BigDecimal minLongitude, BigDecimal maxLongitude,
                                                  Boolean status, Boolean open, Boolean close, Boolean indoor, Boolean outdoor,
                                                  Boolean hygiene, Boolean dirty, Boolean airOut, Boolean noExist, Boolean big,
                                                  Boolean small, Boolean crowded, Boolean quite, Boolean chair);

    List<SmokingArea> findSmokingAreaByQuery(String word,
                                               Boolean temp, Boolean airOut,
                                               Boolean opened,  Boolean closed,
                                               Boolean hygiene, Boolean dirty,
                                               Boolean indoor,  Boolean outdoor,
                                               Boolean big,  Boolean small,
                                               Boolean crowded,  Boolean quite,
                                               Boolean chair, Boolean noExist);
}
