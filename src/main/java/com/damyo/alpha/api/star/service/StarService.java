package com.damyo.alpha.api.star.service;

import com.damyo.alpha.api.auth.domain.UserDetailsImpl;
import com.damyo.alpha.api.smokingarea.domain.SmokingArea;
import com.damyo.alpha.api.star.domain.Star;
import com.damyo.alpha.api.user.domain.User;
import com.damyo.alpha.api.star.controller.dto.AddStarRequest;
import com.damyo.alpha.api.star.controller.dto.StarResponse;
import com.damyo.alpha.api.star.exception.StarException;
import com.damyo.alpha.api.user.exception.UserException;
import com.damyo.alpha.api.smokingarea.domain.SmokingAreaRepository;
import com.damyo.alpha.api.star.domain.StarRepository;
import com.damyo.alpha.api.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.damyo.alpha.api.star.exception.StarErrorCode.STAR_NOT_FOUND;
import static com.damyo.alpha.api.star.exception.StarErrorCode.UNAUTHORIZED_ACTION;
import static com.damyo.alpha.api.user.exception.UserErrorCode.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class StarService {
    private final StarRepository starRepository;
    private final SmokingAreaRepository smokingAreaRepository;

    public void addStar(AddStarRequest request, UserDetailsImpl details) {
        // 예외 처리 해야함
        SmokingArea smokingArea = smokingAreaRepository.findSmokingAreaById(request.saId()).get();
        User user = details.getUser();
        Star saved = starRepository.save(new Star(request, smokingArea, user));
        smokingArea.getStaredList().add(saved);
        user.getStarList().add(saved);
        log.info("[Star]: star create complete");
    }

    public List<StarResponse> getStarList(UserDetailsImpl details) {
        User user = details.getUser();
        List<Star> starList = starRepository.findStarsByUser(user);
        List<StarResponse> starResponseList = new ArrayList<>();
        for (Star star : starList) {
            starResponseList.add(new StarResponse(star));
        }
        log.info("[Star]: star list find complete");
        return starResponseList;
    }

    public void deleteStar(Long starId, UUID id) {
        Star star = starRepository.findStarById(starId)
            .orElseThrow(() -> {
                log.error("[Star]: star not found by id | {}", starId);
                return new StarException(STAR_NOT_FOUND);
            });
        if (!star.getUser().getId().equals(id)) {
            log.error("[Star]: unauthorized action | {}", id);
            throw new StarException(UNAUTHORIZED_ACTION);
        }
        starRepository.deleteById(starId);
        log.info("[Star]: star delete complete");
    }
}
