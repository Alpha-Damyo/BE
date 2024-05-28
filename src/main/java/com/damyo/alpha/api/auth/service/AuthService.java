package com.damyo.alpha.api.auth.service;

import com.damyo.alpha.api.user.domain.User;
import com.damyo.alpha.api.auth.controller.dto.LoginRequest;
import com.damyo.alpha.api.auth.controller.dto.SignUpRequest;
import com.damyo.alpha.api.auth.exception.AuthException;
import com.damyo.alpha.api.auth.jwt.JwtProvider;
import com.damyo.alpha.api.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.damyo.alpha.api.auth.exception.AuthErrorCode.EMAIL_ALREADY_EXIST;
import static com.damyo.alpha.api.auth.exception.AuthErrorCode.EMAIL_NOT_FOUND;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public void signUp(SignUpRequest signUpRequest, String profileUrl) {
        if (userRepository.findUserByEmail(signUpRequest.email()).isPresent()) {
            throw new AuthException(EMAIL_ALREADY_EXIST);
        }
        userRepository.save(new User(signUpRequest, profileUrl));
    }

    public User login(LoginRequest loginRequest) {
        return userRepository.findUserByEmail(loginRequest.email())
                .orElseThrow(() -> new AuthException(EMAIL_NOT_FOUND));
    }
    public User login(SignUpRequest signUpRequest) {
        return userRepository.findUserByEmail(signUpRequest.email())
                .orElseThrow(() -> new AuthException(EMAIL_NOT_FOUND));
    }

    @Transactional
    public String generateToken(User user) {
        return jwtProvider.generate(user.getEmail());
    }
}
