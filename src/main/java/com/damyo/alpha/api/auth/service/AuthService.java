package com.damyo.alpha.api.auth.service;

import com.damyo.alpha.api.user.domain.User;
import com.damyo.alpha.api.auth.controller.dto.LoginRequest;
import com.damyo.alpha.api.auth.controller.dto.SignUpRequest;
import com.damyo.alpha.api.auth.exception.AuthException;
import com.damyo.alpha.api.auth.jwt.JwtProvider;
import com.damyo.alpha.api.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.UUID;

import static com.damyo.alpha.api.auth.exception.AuthErrorCode.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService {
    private static final String GOOGLE_USER_INFO_URI = "https://www.googleapis.com/oauth2/v1/userinfo";
    private static final String NAVER_USER_INFO_URI = "https://openapi.naver.com/v1/nid/me";
    private static final String KAKAO_USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";
    private static final String PROVIDER_GOOGLE = "google";
    private static final String PROVIDER_NAVER = "naver";
    private static final String PROVIDER_KAKAO = "kakao";
    private static final ParameterizedTypeReference<Map<String, Object>> PARAMETERIZED_RESPONSE_TYPE = new ParameterizedTypeReference<Map<String, Object>>() {
    };

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public User signUp(SignUpRequest signUpRequest, String profileUrl) {
        Map<String, Object> userInfo = getUserInfo(signUpRequest.provider(), signUpRequest.token());
        String providerId = getAttributesId(signUpRequest.provider(), userInfo);
        String email = getAttributesEmail(signUpRequest.provider(), userInfo);
        if (userRepository.findUserByProviderId(providerId).isPresent()) {
            throw new AuthException(ACCOUNT_ALREADY_EXIST);
        }
        return userRepository.save(new User(signUpRequest, profileUrl, providerId, email));
    }

    public Map<String, Object> getUserInfo(String provider, String providerToken) {
        return loadUserAttributes(provider, providerToken);
    }
    @SuppressWarnings("unchecked")
    public String getAttributesId(String provider, Map<String, Object> userAttributes) {
        switch (provider) {
            case PROVIDER_GOOGLE -> {
                return (String) userAttributes.get("id");
            }
            case PROVIDER_NAVER -> {
                Map<String, Object> responseAttribute = (Map<String, Object>) userAttributes.get("response");
                return (String) responseAttribute.get("id");
            }
            case PROVIDER_KAKAO -> {
                return userAttributes.get("id").toString();
            }
            default -> throw new AuthException(INVALID_PROVIDER);
        }
    }

    @SuppressWarnings("unchecked")
    private String getAttributesEmail(String provider, Map<String, Object> userAttributes) {
        switch (provider) {
            case PROVIDER_GOOGLE -> {
                return (String) userAttributes.get("email");
            }
            case PROVIDER_NAVER -> {
                Map<String, Object> responseAttribute = (Map<String, Object>) userAttributes.get("response");
                return (String) responseAttribute.get("email");
            }
            case PROVIDER_KAKAO -> {
                return PROVIDER_KAKAO;
            }
            default -> throw new AuthException(INVALID_PROVIDER);
        }
    }

    @Transactional
    public String generateToken(UUID id) {
        return jwtProvider.generate(id.toString());
    }

    private Map<String, Object> loadUserAttributes(String provider, String token) {
        RestTemplate restTemplate = new RestTemplate();
        String url = switch (provider) {
            case PROVIDER_GOOGLE -> GOOGLE_USER_INFO_URI;
            case PROVIDER_NAVER -> NAVER_USER_INFO_URI;
            case PROVIDER_KAKAO -> KAKAO_USER_INFO_URI;
            default -> throw new AuthException(INVALID_PROVIDER);
        };
        URI uri = UriComponentsBuilder
                .fromUriString(url)
                .build()
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        RequestEntity<?> request = new RequestEntity<>(headers, HttpMethod.GET, uri);
        ResponseEntity<Map<String, Object>> exchange = restTemplate.exchange(request, PARAMETERIZED_RESPONSE_TYPE);
        return exchange.getBody();
    }

    public UUID checkIsMember(String providerId) {
        User user = userRepository.findUserByProviderId(providerId).orElseThrow(
                () -> new AuthException(ACCOUNT_NOT_FOUND)
        );
        return user.getId();
    }
}
