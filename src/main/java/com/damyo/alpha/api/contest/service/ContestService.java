package com.damyo.alpha.api.contest.service;

import com.damyo.alpha.api.contest.domain.ContestLike;
import com.damyo.alpha.api.contest.domain.ContestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContestService {

    private final ContestRepository contestRepository;

    @Transactional
    public void likeContestPicture(UUID userId, Long pictureId) {
        contestRepository.findByIdAndUserId(userId, pictureId).ifPresentOrElse(
                l -> {
                    l.updateLike(true);
                },
                () -> {
                    contestRepository.save(ContestLike.builder().userId(userId).pictureId(pictureId).isLike(true).build());
                }
        );
    }

    @Transactional
    public void unlikeContestPicture(UUID userId, Long pictureId) {
        contestRepository.findByIdAndUserId(userId, pictureId).ifPresentOrElse(
                l -> {
                    l.updateLike(false);
                },
                () -> {
                    contestRepository.save(ContestLike.builder().userId(userId).pictureId(pictureId).isLike(false).build());
                }
        );
    }
}
