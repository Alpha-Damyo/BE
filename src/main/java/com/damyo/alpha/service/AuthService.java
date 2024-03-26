package com.damyo.alpha.service;

import com.damyo.alpha.domain.User;
import com.damyo.alpha.dto.request.LoginRequest;
import com.damyo.alpha.dto.request.SignUpRequest;
import com.damyo.alpha.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;

    public void signUp(SignUpRequest signUpRequest) {
        if (signUpRequest.isKakao()) {
            signUpRequest.convertToEmail();
        }
        userRepository.save(new User(signUpRequest));
    }

    public void login(LoginRequest loginRequest) {
        if (loginRequest.isKakao()) {
            loginRequest.convertToEmail();
        }
        User user = userRepository.findUserByEmail(loginRequest.getEmail()).orElseThrow(null);


    }
}
