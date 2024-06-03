package com.damyo.alpha.api.contest.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class ContestRepositoryImpl implements ContestCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QContestLike contest = QContestLike.contestLike;

    @Override
    public Optional<ContestLike> findByIdAndUserId(UUID userId, Long pictureId) {
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(contest)
                .where(contest.userId.eq(userId),
                        contest.pictureId.eq(pictureId)
                )
                .fetchOne());
    }
}
