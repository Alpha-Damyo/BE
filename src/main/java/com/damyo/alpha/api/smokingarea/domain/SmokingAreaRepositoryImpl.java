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
                                                         Boolean status, Boolean open, Boolean close, Boolean indoor, Boolean outdoor,
                                                         Boolean hygiene, Boolean dirty, Boolean airOut, Boolean noExist, Boolean big,
                                                         Boolean small, Boolean crowded, Boolean quite, Boolean chair) {

        List<SmokingArea> areaList = jpaQueryFactory
                .selectFrom(smokingArea)
                .where(
                        latitudeBt(minLatitude, maxLatitude),
                        longitudeBt(minLongitude, maxLongitude),
                        isOpened(open),
                        isClosed(close),
                        isIndoor(indoor),
                        isOutdoor(outdoor),
                        isClean(hygiene),
                        isDirty(dirty),
                        isSmall(small),
                        isBig(big),
                        isCrowded(crowded),
                        isQuite(quite),
                        isChair(chair),
                        isTemp(status),
                        isAir(airOut),
                        isExist(noExist)
                )
                .fetch();

        return areaList;
    }

    @Override
    public List<SmokingArea> findSmokingAreaByQuery(String word,  Boolean temp, Boolean airOut,
                                                    Boolean opened,  Boolean closed,
                                                    Boolean hygiene, Boolean dirty,
                                                    Boolean indoor,  Boolean outdoor,
                                                    Boolean big,  Boolean small,
                                                    Boolean crowded,  Boolean quite,
                                                    Boolean chair, Boolean noExist) {
        List<SmokingArea> areaList = jpaQueryFactory
                .selectFrom(smokingArea)
                .where( wordEq(word),
                        isOpened(opened),
                        isClosed(closed),
                        isIndoor(indoor),
                        isOutdoor(outdoor),
                        isClean(hygiene),
                        isDirty(dirty),
                        isSmall(small),
                        isBig(big),
                        isCrowded(crowded),
                        isQuite(quite),
                        isChair(chair),
                        isTemp(temp),
                        isAir(airOut),
                        isExist(noExist))
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
            return smokingArea.name.like(word+"%");
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
    private BooleanExpression isClean(Boolean clean) {
        if(clean != null) {
            return smokingArea.hygiene.eq(clean);
        }
        return null;
    }
    private BooleanExpression isDirty(Boolean dirty) {
        if(dirty != null) {
            return smokingArea.dirty.eq(dirty);
        }
        return null;
    }
    private BooleanExpression isBig(Boolean big) {
        if(big != null) {
            return smokingArea.big.eq(big);
        }
        return null;
    }
    private BooleanExpression isSmall(Boolean small) {
        if(small != null) {
            return smokingArea.small.eq(small);
        }
        return null;
    }
    private BooleanExpression isCrowded(Boolean crowded) {
        if(crowded != null) {
            return smokingArea.crowded.eq(crowded);
        }
        return null;
    }
    private BooleanExpression isQuite (Boolean quite) {
        if(quite != null) {
            return smokingArea.quite.eq(quite);
        }
        return null;
    }
    private BooleanExpression isChair(Boolean chair) {
        if(chair != null) {
            return smokingArea.chair.eq(chair);
        }
        return null;
    }
    private BooleanExpression isTemp(Boolean status) {
        if(status != null) {
            return smokingArea.status.eq(status);
        }
        return null;
    }
    private BooleanExpression isAir(Boolean air) {
        if(air != null) {
            return smokingArea.airOut.eq(air);
        }
        return null;
    }
    private BooleanExpression isExist(Boolean exist) {
        if(exist != null) {
            return smokingArea.noExist.eq(exist);
        }
        return null;
    }
}
