package com.damyo.alpha.api.picture.domain;

import com.damyo.alpha.api.picture.controller.dto.PictureSliceResponse;

public interface PictureCustomRepository {
    PictureSliceResponse getPictureListByPaging(Long cursorId, Long pageSize, String sortBy, String region);
}
