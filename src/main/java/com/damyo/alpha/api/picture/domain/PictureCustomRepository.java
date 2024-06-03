package com.damyo.alpha.api.picture.domain;

import com.damyo.alpha.api.picture.controller.dto.PictureSliceResponse;

import java.util.List;

public interface PictureCustomRepository {
    PictureSliceResponse getPictureListByPaging(Long cursorId, Long pageSize, String sortBy, String region);
    List<Picture> findPicturesBySmokingArea_Id(String areaId, Long count);
    Long getLikeCountById(Long id);
}
