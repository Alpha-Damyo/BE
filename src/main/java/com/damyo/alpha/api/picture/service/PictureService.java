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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.damyo.alpha.api.picture.exception.PictureErrorCode.PICTURE_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class PictureService {

    private final PictureRepository pictureRepository;
    private final UserRepository userRepository;
    private final SmokingAreaRepository smokingAreaRepository;

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

    public void uploadPicture(UploadPictureRequest uploadPictureRequest, String url) {
        User user = userRepository.findUserById(uploadPictureRequest.userId()).get();
        SmokingArea sa = smokingAreaRepository.findSmokingAreaById(uploadPictureRequest.smokingAreaId());
        pictureRepository.save(
                Picture.builder().
                        pictureUrl(url).
                        user(user).
                        smokingArea(sa).
                        likes(0).
                        createdAt(LocalDateTime.now()).
                        build());
    }

    public PictureSliceResponse getPageContestPicture(Long cursorId, String sortBy, String region) {
        Long pageSize = 24L;
        if(cursorId == 0) cursorId = null;
        PictureSliceResponse pictureSliceResponse = pictureRepository.getPictureListByPaging(cursorId, pageSize, sortBy, region);

        return pictureSliceResponse;
    }

}
