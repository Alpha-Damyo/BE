package com.damyo.alpha.api.smokingarea.domain;

import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface SmokingAreaCustomRepository {
    List<SmokingArea> findSmokingAreaByCoordinate(BigDecimal minLatitude, BigDecimal maxLatitude,
                                                  BigDecimal minLongitude, BigDecimal maxLongitude,
                                                  Boolean status, Boolean open, Boolean close, Boolean indoor, Boolean outdoor);

    List<SmokingArea> findSmokingAreaByQuery(String word,
                                               Boolean temp,
                                               Boolean opened,  Boolean closed,
                                               Boolean indoor,  Boolean outdoor);
}
