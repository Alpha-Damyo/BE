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

import static com.damyo.alpha.api.auth.exception.AuthErrorCode.INVALID_REFRESH_TOKEN;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("${secret.at}")
    private String accessSecret;
    @Value("${secret.rt}")
    private String refreshSecret;
    private static final int ACCESS_EXPIRED_DURATION = 24;
    private static final int REFRESH_EXPIRED_DURATION = 24 * 365;
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

    public TokenResponse reissueToken(String refreshToken, UUID id) {
        if (!validateRefresh(refreshToken)) {
            throw new AuthException(INVALID_REFRESH_TOKEN);
        }
        return generate(id.toString());
    }

    public TokenResponse generate(String id) {
        Claims claims = Jwts.claims();
        claims.put("id", id);
        return TokenResponse.builder()
                .accessToken(generateAccessToken(claims))
                .refreshToken(generateRefreshToken())
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

    private String generateRefreshToken() {
        return Jwts.builder()
                .setIssuedAt(issuedAt())
                .setExpiration(refreshExpiredAt())
                .signWith(refreshKey,SignatureAlgorithm.HS512)
                .compact();
    }

    private Date accessExpiredAt() {
        LocalDateTime now = LocalDateTime.now();
        return Date.from(now.plusHours(ACCESS_EXPIRED_DURATION).atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date refreshExpiredAt() {
        LocalDateTime now = LocalDateTime.now();
        return Date.from(now.plusHours(REFRESH_EXPIRED_DURATION).atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date issuedAt() {
        LocalDateTime now = LocalDateTime.now();
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }

    private boolean validateRefresh(String refreshToken) {
        try {
            Date expiration = Jwts.parserBuilder()
                    .setSigningKey(refreshKey)
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody()
                    .getExpiration();
            return !expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String validateAccessAndGetId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(accessKey)
                .build()
                .parseClaimsJws(token)
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
