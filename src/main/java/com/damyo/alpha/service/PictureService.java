package com.damyo.alpha.service;

import com.damyo.alpha.domain.Picture;
import com.damyo.alpha.domain.SmokingArea;
import com.damyo.alpha.domain.User;
import com.damyo.alpha.dto.request.UploadPictureRequest;
import com.damyo.alpha.dto.response.PictureResponse;
import com.damyo.alpha.repository.PictureRepository;
import com.damyo.alpha.repository.SmokingAreaRepository;
import com.damyo.alpha.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PictureService {

    private final PictureRepository pictureRepository;
    private final UserRepository userRepository;
    private final SmokingAreaRepository smokingAreaRepository;

    public PictureResponse getPicture(Long id) {
        Picture picture = pictureRepository.findPictureById(id).orElseThrow(null);
        return new PictureResponse(picture);
    }

    public List<PictureResponse> getPicturesByUser(UUID id) {
        List<Picture> pictures = pictureRepository.findPicturesByUser_id(id);
        return getPictureListResponse(pictures);
    }

    public List<PictureResponse> getPicturesBySmokingArea(String id) {
        List<Picture> pictures = pictureRepository.findPicturesBySmokingArea_Id(id);
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

}
