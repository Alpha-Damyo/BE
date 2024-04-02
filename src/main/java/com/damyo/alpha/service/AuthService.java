package com.damyo.alpha.service;

import com.damyo.alpha.domain.User;
import com.damyo.alpha.dto.request.LoginRequest;
import com.damyo.alpha.dto.request.SignUpRequest;
import com.damyo.alpha.security.infrastructure.JwtProvider;
import com.damyo.alpha.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public void signUp(SignUpRequest signUpRequest) {
        if (signUpRequest.isKakao()) {
            signUpRequest.convertToEmail();
        }
        if (userRepository.findUserByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new RuntimeException();
        }
        userRepository.save(new User(signUpRequest));
    }

    public User login(LoginRequest loginRequest) {
        if (loginRequest.isKakao()) {
            loginRequest.convertToEmail();
        }
        return userRepository.findUserByEmail(loginRequest.getEmail()).orElseThrow(RuntimeException::new);
    }
    public User login(SignUpRequest signUpRequest) {
        return userRepository.findUserByEmail(signUpRequest.getEmail()).orElseThrow(RuntimeException::new);
    }

    @Transactional
    public String generateToken(User user) {
        return jwtProvider.generate(user.getEmail());
    }
}
