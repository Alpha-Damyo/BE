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
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String CONTENT_HEADER = "Content-type";
    private static final String GRANT_TYPE = "Bearer ";
    private static final String FORM_TYPE = "application/x-www-form-urlencoded:utf-8";

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public String login(String token, String provider) {
        Map<String, Object> userInfo = getUserInfo(token, provider);
        String providerId = getAttributesId(userInfo, provider);
        User user = findOrCreateUser(providerId, provider);
        return jwtProvider.generate(user.getId().toString());
    }

    public String generateTestToken(String providerId) {
        User user = userRepository.findUserByProviderId(providerId).orElseThrow(
                () -> new AuthException(ACCOUNT_NOT_FOUND)
        );
        return jwtProvider.generate(user.getId().toString());
    }

    private Map<String, Object> getUserInfo(String token, String provider) {
        return loadUserAttributes(token, provider);
    }

    private Map<String, Object> loadUserAttributes(String token, String provider) {
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
        headers.add(CONTENT_HEADER, FORM_TYPE);
        headers.add(AUTHORIZATION_HEADER, GRANT_TYPE + token);
        RequestEntity<?> request = new RequestEntity<>(headers, HttpMethod.GET, uri);

        try {
            ResponseEntity<Map<String, Object>> exchange = restTemplate.exchange(request, PARAMETERIZED_RESPONSE_TYPE);
            return exchange.getBody();
        } catch (HttpClientErrorException e) {
            throw new AuthException(FAIL_GET_INFO);
        }
    }
    @SuppressWarnings("unchecked")
    private String getAttributesId(Map<String, Object> userAttributes, String provider) {
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

    private User findOrCreateUser(String provider, String providerId) {
        // TO DO: 기본 이름, 프로필 설정하기
        return userRepository.findUserByProviderId(providerId).orElseGet(
                () -> userRepository.save(new User("유저", provider, providerId, null))
        );
    }
}
