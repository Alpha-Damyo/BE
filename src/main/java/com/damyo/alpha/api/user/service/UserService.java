package com.damyo.alpha.api.user.service;

import com.damyo.alpha.api.user.domain.User;
import com.damyo.alpha.api.user.controller.dto.UpdateUserNameRequest;
import com.damyo.alpha.api.user.controller.dto.UpdateUserProfileRequest;
import com.damyo.alpha.api.user.controller.dto.UpdateUserScoreRequest;
import com.damyo.alpha.api.user.controller.dto.UserResponse;
import com.damyo.alpha.api.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getUser(UUID id) {
        User user = userRepository.findUserById(id).orElseThrow(null);
        return new UserResponse(user);
    }

    @Transactional
    public void updateName(UpdateUserNameRequest updateUserNameRequest) {
        userRepository.updateNameByEmail(updateUserNameRequest.name(), updateUserNameRequest.email());
    }

    @Transactional
    public void updateProfile(UpdateUserProfileRequest request) {
        userRepository.updateProfileUrlByEmail(request.profileUrl(), request.email());
    }

    @Transactional
    public void updateContribution(UpdateUserScoreRequest request) {
        userRepository.updateContributionByEmail(request.increment(), request.email());
    }

    @Transactional
    public void deleteUser(UUID userId) {
        userRepository.deleteUserById(userId);
    }
}
