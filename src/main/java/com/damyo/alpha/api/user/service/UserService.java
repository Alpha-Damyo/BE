package com.damyo.alpha.api.user.service;

import com.damyo.alpha.api.auth.domain.UserDetailsImpl;
import com.damyo.alpha.api.user.domain.User;
import com.damyo.alpha.api.user.controller.dto.UserResponse;
import com.damyo.alpha.api.user.domain.UserRepository;
import com.damyo.alpha.api.user.exception.UserErrorCode;
import com.damyo.alpha.api.user.exception.UserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static com.damyo.alpha.api.user.exception.UserErrorCode.INVALID_NAME;
import static com.damyo.alpha.api.user.exception.UserErrorCode.USER_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getUser(UUID id) {
        User user = userRepository.findUserById(id)
            .orElseThrow(() -> {
                log.error("[User]: user not found by id | {}", id);
                return new UserException(USER_NOT_FOUND);
            }
        );
        float percentage = userRepository.getPercentageByContribution(id);
        int top1Contribution = userRepository.getTop1Contribution();
        log.info("[User]: user find complete");
        return new UserResponse(user, percentage, top1Contribution - user.getContribution());
    }

    @Transactional
    public void updateName(UserDetailsImpl details, String name) {
        if (details.getName().equals(name)) {
            log.error("[User]: user name is invalid | {}", name);
            throw new UserException(INVALID_NAME);
        }
        log.info("[User]: user name update complete");
        userRepository.updateNameById(name, details.getId());
    }

    @Transactional
    public String updateProfile(UserDetailsImpl details, String profileUrl) {
        String prevUrl = details.getProfileUrl();
        userRepository.updateProfileUrlById(profileUrl, details.getId());
        log.info("[User]: user profile update complete");
        return prevUrl;
    }

    @Transactional
    public void updateContribution(UUID id, int increment) {
        userRepository.updateContributionById(increment, id);
        log.info("[User]: user contribution update complete");
    }

    @Transactional
    public void deleteUser(UUID id) {
        userRepository.deleteUserById(id);
        log.info("[User]: delete complete");
    }
}
