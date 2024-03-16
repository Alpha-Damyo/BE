package com.damyo.alpha.service;

import com.damyo.alpha.domain.Picture;
import com.damyo.alpha.dto.response.PictureResponse;
import com.damyo.alpha.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PictureService {

    private PictureRepository pictureRepository;

    public PictureResponse getPicture(Long id) {
        Picture picture = pictureRepository.findPictureById(id).orElseThrow(null);
        return PictureResponse.builder()
                .id(id)
                .pictureUrl(picture.getPictureUrl())
                .createdAt(picture.getCreatedAt())
                .likes(picture.getLikes())
                .build();
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
            PictureResponse pictureResponse = PictureResponse.builder()
                    .id(picture.getId())
                    .pictureUrl(picture.getPictureUrl())
                    .createdAt(picture.getCreatedAt())
                    .likes(picture.getLikes())
                    .build();
            pictureList.add(pictureResponse);
        }
        return pictureList;
    }
}
