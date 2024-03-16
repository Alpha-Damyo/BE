package com.damyo.alpha.service;

import com.damyo.alpha.domain.User;
import com.damyo.alpha.dto.request.UpdateUserNameRequest;
import com.damyo.alpha.dto.request.UpdateUserProfileRequest;
import com.damyo.alpha.dto.request.UpdateUserScoreRequest;
import com.damyo.alpha.dto.response.UserResponse;
import com.damyo.alpha.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;

    public UserResponse getUser(UUID id) {
        User user = userRepository.findUserById(id).orElseThrow(null);

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .profileUrl(user.getProfileUrl())
                .contribution(user.getContribution())
                .gender(user.getGender())
                .age(user.getAge())
                .build();
    }

    public void updateName(UpdateUserNameRequest updateUserNameRequest) {
        userRepository.updateNameByEmail(updateUserNameRequest.getName(), updateUserNameRequest.getEmail());
    }

    public void updateProfile(UpdateUserProfileRequest request) {
        userRepository.updateProfileUrlByEmail(request.getProfileUrl(), request.getEmail());
    }

    public void updateContribution(UpdateUserScoreRequest request) {
        userRepository.updateContributionByEmail(request.getIncrement(), request.getEmail());
    }

    public void deleteUser(UUID userId) {
        userRepository.deleteUserById(userId);
    }
}
