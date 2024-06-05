package com.damyo.alpha.api.picture.domain;

import com.damyo.alpha.api.contest.domain.QContestLike;
import com.damyo.alpha.api.picture.controller.dto.LikesRankResponse;
import com.damyo.alpha.api.picture.controller.dto.PictureResponse;
import com.damyo.alpha.api.picture.controller.dto.PictureSliceResponse;
import com.damyo.alpha.api.smokingarea.domain.QSmokingArea;
import com.damyo.alpha.api.user.domain.QUser;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class PictureRepositoryImpl implements PictureCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private QPicture picture = QPicture.picture;
    private QSmokingArea smokingArea = QSmokingArea.smokingArea;
    private QContestLike contestLike = QContestLike.contestLike;
    private QUser user = QUser.user;

    @Override
    public PictureSliceResponse getPictureListByPaging(Long cursorId, Long pageSize, String sortBy, String region, UUID userId) {
        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(sortBy);

        List<PictureResponse> pictureList = jpaQueryFactory
                .select(Projections.constructor(
                        PictureResponse.class,
                        picture.id, picture.pictureUrl, picture.createdAt, picture.likes
                        )
                )
                .from(picture)
                .innerJoin(picture.smokingArea, smokingArea)
                .leftJoin(contestLike)
                .on(picture.id.eq(contestLike.pictureId),
                        contestLike.userId.eq(userId))
                .where(cursorId(cursorId),
                        regionEq(region))
                .orderBy(orderSpecifiers)
                .limit(pageSize + 1)
                .distinct()
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

    @Override
    public Long getLikeCountById(Long id) {
        Long count = jpaQueryFactory
                .select(picture.likes)
                .from(picture)
                .where(picture.id.eq(id))
                .fetchOne();

        return count;
    }

    @Override
    public List<LikesRankResponse> getRanking() {
        List<Tuple> result = jpaQueryFactory
                .select(user.id, user.name, user.profileUrl, picture.likes.sum())
                .from(user)
                .leftJoin(user, picture.user)
                .groupBy(user.id, user.name)
                .orderBy(picture.likes.sum().desc().nullsLast())
                .fetch();

        List<LikesRankResponse> rankedUsers = IntStream.range(0, result.size())
                .mapToObj(i -> new LikesRankResponse(
                        result.get(i).get(user.id),
                        result.get(i).get(user.name),
                        result.get(i).get(user.profileUrl),
                        result.get(i).get(picture.likes.sum()),
                        i + 1L))
                .collect(Collectors.toList());

        return rankedUsers;
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
