package com.damyo.alpha.api.smokingarea.domain;

import com.damyo.alpha.api.picture.domain.QPicture;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
public class SmokingAreaRepositoryImpl implements SmokingAreaCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private QSmokingArea smokingArea = QSmokingArea.smokingArea;

    @Override
    public List<SmokingArea> findSmokingAreaByCoordinate(BigDecimal minLatitude, BigDecimal maxLatitude,
                                                         BigDecimal minLongitude, BigDecimal maxLongitude,
                                                         Boolean status, Boolean open, Boolean close, Boolean indoor, Boolean outdoor) {

        List<SmokingArea> areaList = jpaQueryFactory
                .selectFrom(smokingArea)
                .where(
                        latitudeBt(minLatitude, maxLatitude),
                        longitudeBt(minLongitude, maxLongitude),
                        isOpened(open),
                        isClosed(close),
                        isIndoor(indoor),
                        isOutdoor(outdoor),
                        isTemp(status),
                        isActive(true)
                )
                .fetch();

        return areaList;
    }

    @Override
    public List<SmokingArea> findSmokingAreaByQuery(String word,  Boolean temp,
                                                    Boolean opened,  Boolean closed,
                                                    Boolean indoor,  Boolean outdoor) {
        List<SmokingArea> areaList = jpaQueryFactory
                .selectFrom(smokingArea)
                .where( wordEq(word),
                        isOpened(opened),
                        isClosed(closed),
                        isIndoor(indoor),
                        isOutdoor(outdoor),
                        isTemp(temp),
                        isActive(true))
                .fetch();
        return areaList;
    }

    private BooleanExpression latitudeBt(BigDecimal min, BigDecimal max) {
        return smokingArea.latitude.between(min, max);
    }

    private BooleanExpression longitudeBt(BigDecimal min, BigDecimal max) {
        return smokingArea.longitude.between(min, max);
    }

    private BooleanExpression wordEq(String word) {
        if(StringUtils.hasText(word)) {
            return smokingArea.name.like("%"+word+"%");
        }
        return null;
    }

    private BooleanExpression isOpened(Boolean open) {
        if(open != null) {
            return smokingArea.opened.eq(open);
        }
        return null;
    }
    private BooleanExpression isClosed(Boolean close) {
        if(close != null) {
            return smokingArea.closed.eq(close);
        }
        return null;
    }
    private BooleanExpression isIndoor(Boolean indoor) {
        if(indoor != null) {
            return smokingArea.indoor.eq(indoor);
        }
        return null;
    }
    private BooleanExpression isOutdoor(Boolean outdoor) {
        if(outdoor != null) {
            return smokingArea.outdoor.eq(outdoor);
        }
        return null;
    }
    private BooleanExpression isTemp(Boolean status) {
        if(status != null) {
            return smokingArea.status.eq(status);
        }
        return null;
    }
    private BooleanExpression isActive(Boolean active) {
        if(active != null) {
            return smokingArea.status.eq(active);
        }
        return null;
    }
}
