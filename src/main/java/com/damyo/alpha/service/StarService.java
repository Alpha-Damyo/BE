package com.damyo.alpha.service;

import com.damyo.alpha.domain.SmokingArea;
import com.damyo.alpha.domain.Star;
import com.damyo.alpha.domain.User;
import com.damyo.alpha.dto.request.AddStarRequest;
import com.damyo.alpha.dto.response.StarResponse;
import com.damyo.alpha.exception.exception.UserException;
import com.damyo.alpha.repository.SmokingAreaRepository;
import com.damyo.alpha.repository.StarRepository;
import com.damyo.alpha.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.damyo.alpha.exception.errorCode.UserErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class StarService {
    private final StarRepository starRepository;
    private final UserRepository userRepository;
    private final SmokingAreaRepository smokingAreaRepository;

    public void addStar(AddStarRequest request, UUID id) {
        SmokingArea smokingArea = smokingAreaRepository.findSmokingAreaById(request.saId());
        User user = userRepository.findUserById(id).orElseThrow(
                () -> new UserException(USER_NOT_FOUND)
        );
        Star saved = starRepository.save(
                Star.builder().customName(request.customName())
                        .groupName(request.groupName())
                        .user(user)
                        .smokingArea(smokingArea)
                        .build()
        );
        smokingArea.getStaredList().add(saved);
        user.getStarList().add(saved);
    }

    public List<StarResponse> getStarList(UUID id) {
        User user = userRepository.findUserById(id).orElseThrow(
                () -> new UserException(USER_NOT_FOUND)
        );
        List<Star> starList = starRepository.findStarsByUser(user);
        List<StarResponse> starResponseList = new ArrayList<>();
        for (Star star : starList) {
            starResponseList.add(new StarResponse(star));
        }
        return starResponseList;
    }

    public void deleteStar(Long id) {
        starRepository.deleteById(id);
    }
}
