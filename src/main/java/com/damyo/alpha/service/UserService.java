package com.damyo.alpha.service;

import com.damyo.alpha.domain.User;
import com.damyo.alpha.dto.request.UpdateUserNameRequest;
import com.damyo.alpha.dto.request.UpdateUserProfileRequest;
import com.damyo.alpha.dto.request.UpdateUserScoreRequest;
import com.damyo.alpha.dto.response.UserResponse;
import com.damyo.alpha.repository.UserRepository;
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
