package com.damyo.alpha.api.contest.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContestRepository extends JpaRepository<ContestLike, Long>, ContestCustomRepository {
    ContestLike save(ContestLike contestLike);

    @Override
    Optional<ContestLike> findById(Long id);
}
