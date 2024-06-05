package com.damyo.alpha.api.picture.domain;

import com.damyo.alpha.api.picture.controller.dto.PictureSliceResponse;

import java.util.List;
import java.util.UUID;

public interface PictureCustomRepository {
    PictureSliceResponse getPictureListByPaging(Long cursorId, Long pageSize, String sortBy, String region, UUID userId);
    List<Picture> findPicturesBySmokingArea_Id(String areaId, Long count);
    Long getLikeCountById(Long id);

    void getTopRanking();
    void getNearRanking(UUID userID);
}
