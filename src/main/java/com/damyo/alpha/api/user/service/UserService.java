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

import java.util.UUID;

import static com.damyo.alpha.api.user.exception.UserErrorCode.INVALID_NAME;
import static com.damyo.alpha.api.user.exception.UserErrorCode.USER_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getUser(UUID id) {
        User user = userRepository.findUserById(id).orElseThrow(
                () -> new UserException(USER_NOT_FOUND)
        );
        return new UserResponse(user);
    }

    @Transactional
    public void updateName(UserDetailsImpl details, String name) {
        if (details.getName().equals(name)) {
            throw new UserException(INVALID_NAME);
        }
        userRepository.updateNameById(name, details.getId());
    }

    @Transactional
    public String updateProfile(UserDetailsImpl details, String profileUrl) {
        String prevUrl = details.getProfileUrl();
        userRepository.updateProfileUrlById(profileUrl, details.getId());
        return prevUrl;
    }

    @Transactional
    public void updateContribution(UUID id, int increment) {
        userRepository.updateContributionById(increment, id);
    }

    @Transactional
    public void deleteUser(UUID id) {
        userRepository.deleteUserById(id);
    }
}
