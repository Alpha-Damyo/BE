package com.damyo.alpha.api.picture.service;

import com.damyo.alpha.api.picture.controller.dto.PictureSliceResponse;
import com.damyo.alpha.api.picture.domain.Picture;
import com.damyo.alpha.api.picture.exception.PictureErrorCode;
import com.damyo.alpha.api.picture.exception.PictureException;
import com.damyo.alpha.api.smokingarea.domain.SmokingArea;
import com.damyo.alpha.api.user.domain.User;
import com.damyo.alpha.api.picture.controller.dto.UploadPictureRequest;
import com.damyo.alpha.api.picture.controller.dto.PictureResponse;
import com.damyo.alpha.api.picture.domain.PictureRepository;
import com.damyo.alpha.api.smokingarea.domain.SmokingAreaRepository;
import com.damyo.alpha.api.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static com.damyo.alpha.api.picture.exception.PictureErrorCode.PICTURE_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class PictureService {

    private final PictureRepository pictureRepository;
    private final UserRepository userRepository;
    private final SmokingAreaRepository smokingAreaRepository;

    @Autowired
    private RedisTemplate<String, Object> countTemplate;

    public PictureResponse getPicture(Long id) {
        Picture picture = pictureRepository.findPictureById(id).orElseThrow(
                () -> new PictureException(PICTURE_NOT_FOUND)
        );
        return new PictureResponse(picture);
    }

    public List<PictureResponse> getPicturesByUser(UUID id) {
        List<Picture> pictures = pictureRepository.findPicturesByUser_id(id);
        return getPictureListResponse(pictures);
    }

    public List<PictureResponse> getPicturesBySmokingArea(String id, Long count) {
        List<Picture> pictures = pictureRepository.findPicturesBySmokingArea_Id(id, count);
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
        User user = userRepository.findUserById(userId).get();
        SmokingArea sa = smokingAreaRepository.findSmokingAreaById(areaId).get();
        pictureRepository.save(
                Picture.builder().
                        pictureUrl(url).
                        user(user).
                        smokingArea(sa).
                        likes(0L).
                        createdAt(LocalDateTime.now()).
                        build());
    }

    public PictureSliceResponse getPageContestPicture(Long cursorId, String sortBy, String region, UUID userId) {
        Long pageSize = 24L;
        if(cursorId == 0) cursorId = null;
        PictureSliceResponse pictureSliceResponse = pictureRepository.getPictureListByPaging(cursorId, pageSize, sortBy, region, userId);

        return pictureSliceResponse;
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
        System.out.println("likes update complete");
    }
}
