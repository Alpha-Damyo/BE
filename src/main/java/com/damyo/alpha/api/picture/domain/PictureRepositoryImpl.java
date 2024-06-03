package com.damyo.alpha.api.picture.domain;

import com.damyo.alpha.api.picture.controller.dto.PictureResponse;
import com.damyo.alpha.api.picture.controller.dto.PictureSliceResponse;
import com.damyo.alpha.api.smokingarea.domain.QSmokingArea;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PictureRepositoryImpl implements PictureCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private QPicture picture = QPicture.picture;
    private QSmokingArea smokingArea = QSmokingArea.smokingArea;

    @Override
    public PictureSliceResponse getPictureListByPaging(Long cursorId, Long pageSize, String sortBy, String region) {
        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(sortBy);

        List<PictureResponse> pictureList = jpaQueryFactory
                .select(Projections.constructor(
                        PictureResponse.class,
                        picture.id, picture.pictureUrl, picture.createdAt, picture.likes
                        )
                )
                .from(picture)
                .innerJoin(picture.smokingArea, smokingArea)
                .where(cursorId(cursorId),
                        regionEq(region))
                .orderBy(orderSpecifiers)
                .limit(pageSize + 1)
                .fetch();

        boolean hasNext = false;
        if(pictureList.size() > pageSize) {
            pictureList.remove(pageSize);
            hasNext = true;
        }

        Long lastCursorId = null;

        if(hasNext && pictureList.size() != 0) {
            lastCursorId = pictureList.get(pictureList.size() - 1).id();
        }

        return new PictureSliceResponse(lastCursorId, hasNext, pictureList);

    }

    @Override
    public List<Picture> findPicturesBySmokingArea_Id(String areaId, Long count) {
        List<Picture> pictureList = jpaQueryFactory
                .selectFrom(picture)
                .innerJoin(picture.smokingArea, smokingArea)
                .where(areaEq(areaId))
                .orderBy(picture.createdAt.asc())
                .limit(count)
                .fetch();

        return pictureList;
    }

    private BooleanExpression cursorId(Long cursorId) {
        return cursorId == null ? null : picture.id.gt(cursorId);
    }

    private BooleanExpression regionEq(String region) {
        if(StringUtils.hasText(region)){
            return picture.smokingArea.id.like(region);
        }
        return null;
    }

    private BooleanExpression areaEq(String areaId) {
        if(StringUtils.hasText(areaId)){
            return picture.smokingArea.id.eq(areaId);
        }
        return null;
    }

    private OrderSpecifier[] createOrderSpecifier(String sortBy) {
        List<OrderSpecifier> orderSpecifierList = new ArrayList<>();

        if(sortBy.equals("date")) {
            orderSpecifierList.add(new OrderSpecifier(Order.DESC, picture.createdAt));
        }
        else if(sortBy.equals("like")) {
            orderSpecifierList.add(new OrderSpecifier(Order.DESC, picture.likes));
        }
        return orderSpecifierList.toArray(new OrderSpecifier[orderSpecifierList.size()]);
    }

}
