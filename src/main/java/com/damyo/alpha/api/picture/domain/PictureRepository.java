package com.damyo.alpha.api.picture.domain;

import com.damyo.alpha.api.picture.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PictureRepository extends JpaRepository<Picture, Long> {
    Picture save(Picture picture);
    Optional<Picture> findPictureById(Long id);
    void deletePictureById(Long id);

    List<Picture> findPicturesByUser_id(UUID id);
    List<Picture> findPicturesBySmokingArea_Id(String id);
}
