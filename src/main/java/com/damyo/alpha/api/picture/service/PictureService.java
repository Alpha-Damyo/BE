package com.damyo.alpha.api.picture.service;

import com.damyo.alpha.api.picture.controller.dto.*;
import com.damyo.alpha.api.picture.domain.Picture;
import com.damyo.alpha.api.picture.exception.PictureErrorCode;
import com.damyo.alpha.api.picture.exception.PictureException;
import com.damyo.alpha.api.smokingarea.domain.SmokingArea;
import com.damyo.alpha.api.smokingarea.exception.AreaException;
import com.damyo.alpha.api.user.domain.User;
import com.damyo.alpha.api.picture.domain.PictureRepository;
import com.damyo.alpha.api.smokingarea.domain.SmokingAreaRepository;
import com.damyo.alpha.api.user.domain.UserRepository;
import com.damyo.alpha.api.user.exception.UserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.damyo.alpha.api.picture.exception.PictureErrorCode.PICTURE_NOT_FOUND;
import static com.damyo.alpha.api.smokingarea.exception.AreaErrorCode.NOT_FOUND_ID;
import static com.damyo.alpha.api.user.exception.UserErrorCode.USER_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Service
public class PictureService {

    private final PictureRepository pictureRepository;
    private final UserRepository userRepository;
    private final SmokingAreaRepository smokingAreaRepository;

    @Autowired
    private RedisTemplate<String, Object> countTemplate;

    @Cacheable(value="pictureCache", key="#id", cacheManager="contentCacheManager")
    public PictureResponse getPicture(Long id) {
        Picture picture = pictureRepository.findPictureById(id)
            .orElseThrow(() -> {
                log.error("[Picture]: image not found by id | {}", id);
                return new PictureException(PICTURE_NOT_FOUND);
            });
        log.info("[Picture]: load picture by id | {}", id);
        return new PictureResponse(picture);
    }

    public List<PictureResponse> getPicturesByUser(UUID id) {
        List<Picture> pictures = pictureRepository.findPicturesByUser_id(id);
        log.info("[Picture]: load pictures by user | {}", id);
        return getPictureListResponse(pictures);
    }

    public List<PictureResponse> getPicturesBySmokingArea(String id, Long count) {
        List<Picture> pictures = pictureRepository.findPicturesBySmokingArea_Id(id, count);
        log.info("[Picture]: load pictures by area | {}", id);
        return getPictureListResponse(pictures);
    }

    private List<PictureResponse> getPictureListResponse(List<Picture> pictures) {
        List<PictureResponse> pictureList = new ArrayList<>();
        for (Picture picture: pictures) {
            pictureList.add(new PictureResponse(picture));
        }
        return pictureList;
    }

    public void uploadPicture(UUID userId, String areaId, String url) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> {
                    log.error("user is not found by id | {}", userId);
                    return new UserException(USER_NOT_FOUND);
                });
        SmokingArea sa = smokingAreaRepository.findSmokingAreaById(areaId)
                .orElseThrow(() -> {
                    log.error("area is not found by id | {}", areaId);
                    return new AreaException(NOT_FOUND_ID);
                });
        pictureRepository.save(
                Picture.builder().
                        pictureUrl(url).
                        user(user).
                        smokingArea(sa).
                        likes(0L).
                        createdAt(LocalDateTime.now()).
                        build());
        log.info("[Picture]: upload picture complete");
    }

    public PictureSliceResponse getPageContestPicture(Long cursorId, String sortBy, String region, UUID userId) {
        Long pageSize = 24L;
        if(cursorId == 0) cursorId = null;
        PictureSliceResponse pictureSliceResponse = pictureRepository.getPictureListByPaging(cursorId, pageSize, sortBy, region, userId);

        return pictureSliceResponse;
    }

    public AllRankResponse getContestRanking(UUID userId) {
        List<LikesRankResponse> rank =  pictureRepository.getRanking();

        int targetIdx = IntStream.range(0, rank.size())
                .filter(i -> rank.get(i).userId().equals(userId))
                .findFirst()
                .orElse(-1);

        if (targetIdx == -1) {
            log.error("[Picture]: user id not found | {}", userId);
            throw new IllegalArgumentException("User ID not found");
        }

        int startIndex = Math.max(0, targetIdx - 3);
        int endIndex = Math.min(rank.size(), targetIdx + 4);
        int topIndex = Math.min(3, rank.size());

        List<LikesRankResponse> topUsers = IntStream.range(0, topIndex)
                .mapToObj(rank::get)
                .collect(Collectors.toList());

        List<LikesRankResponse> surroundingUsers = IntStream.range(startIndex, endIndex)
                .mapToObj(rank::get)
                .collect(Collectors.toList());

        return new AllRankResponse(topUsers, surroundingUsers);
    }

    @Transactional
    public void increaseLikeCount(Long pictureId) {
        HashOperations<String, String, Object> hashOperations = countTemplate.opsForHash();
        String key = "likeId::" + pictureId.toString();
        String hashKey = "likes";

        if(hashOperations.get(key, hashKey) == null) {
            hashOperations.put(key, hashKey, pictureRepository.getLikeCountById(pictureId));
        }
        hashOperations.increment(key, hashKey, 1L);
        System.out.println(hashOperations.get(key, hashKey));
    }

    @Transactional
    public void decreaseLikeCount(Long pictureId) {
        HashOperations<String, String, Object> hashOperations = countTemplate.opsForHash();
        String key = "likeId::" + pictureId.toString();
        String hashKey = "likes";

        if(hashOperations.get(key, hashKey) == null) {
            hashOperations.put(key, hashKey, pictureRepository.getLikeCountById(pictureId));
        }
        hashOperations.increment(key, hashKey, -1L);
        System.out.println(hashOperations.get(key, hashKey));
    }

    @Scheduled(fixedDelay = 1000L * 30L)
    @Transactional
    public void updateLikeCount() {
        String hashKey = "likes";
        Set<String> RedisKey = countTemplate.keys("likeId*");
        Iterator<String> it = RedisKey.iterator();

        while(it.hasNext() == true) {
            String data = it.next();
            Long pictureId = Long.parseLong(data.split("::")[1]);
            if(countTemplate.opsForHash().get(data, hashKey) == null){
                break;
            }
            Long likeCount = Long.parseLong((String.valueOf(countTemplate.opsForHash().get(data, hashKey))));
            pictureRepository.findById(pictureId).ifPresent(p -> {
                p.updateLikeCount(likeCount);
            });
            countTemplate.opsForHash().delete(data, hashKey);
        }
        // System.out.println("likes update complete");
    }
}
