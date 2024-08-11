package com.damyo.alpha.api.auth.jwt;

import com.damyo.alpha.api.auth.controller.dto.TokenResponse;
import com.damyo.alpha.api.auth.exception.AuthException;
import com.damyo.alpha.api.auth.service.UserDetailServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import static com.damyo.alpha.api.auth.exception.AuthErrorCode.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("${secret.at}")
    private String accessSecret;
    @Value("${secret.rt}")
    private String refreshSecret;
    @Value("${expiration.at}")
    private int accessExpiration;
    @Value("${expiration.rt}")
    private int refreshExpiration;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String GRANT_TYPE = "Bearer ";
    private Key accessKey;
    private Key refreshKey;
    private final UserDetailServiceImpl userDetailService;

    @PostConstruct
    private void init() {
        accessKey = Keys.hmacShaKeyFor(accessSecret.getBytes());
        refreshKey = Keys.hmacShaKeyFor(refreshSecret.getBytes());
    }

    public TokenResponse generate(String id) {
        Claims claims = Jwts.claims();
        claims.put("id", id);
        return TokenResponse.builder()
                .accessToken(generateAccessToken(claims))
                .refreshToken(generateRefreshToken(claims))
                .build();
    }

    private String generateAccessToken(Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt())
                .setExpiration(accessExpiredAt())
                .signWith(accessKey, SignatureAlgorithm.HS512)
                .compact();
    }

    private String generateRefreshToken(Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt())
                .setExpiration(refreshExpiredAt())
                .signWith(refreshKey,SignatureAlgorithm.HS512)
                .compact();
    }

    private Date accessExpiredAt() {
        LocalDateTime now = LocalDateTime.now();
        return Date.from(now.plusSeconds(accessExpiration).atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date refreshExpiredAt() {
        LocalDateTime now = LocalDateTime.now();
        return Date.from(now.plusSeconds(refreshExpiration).atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date issuedAt() {
        LocalDateTime now = LocalDateTime.now();
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }

    public String validateRefreshAndGetId(String refreshToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(refreshKey)
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody()
                    .get("id", String.class);
        } catch (Exception e) {
            log.info("3-1");
            throw new AuthException(INVALID_REFRESH_TOKEN);
        }
    }

    public String validateAccessAndGetId(String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(accessKey)
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .get("id", String.class);
    }

    public Authentication createAuthentication(UUID id) {
        UserDetails userDetails = userDetailService.loadUserByUsername(id.toString());
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(GRANT_TYPE)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
