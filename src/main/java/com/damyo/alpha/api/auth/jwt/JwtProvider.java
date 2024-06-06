package com.damyo.alpha.api.auth.jwt;

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

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;
    private static final int EXPIRED_DURATION = 24;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String GRANT_TYPE = "Bearer ";
    private Key key;

    private final UserDetailServiceImpl userDetailService;

    @PostConstruct
    private void init() {
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generate(UUID id) {
        Claims claims = Jwts.claims();
        claims.put("id", id);
        return generateToken(claims);
    }

    private String generateToken(Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt())
                .setExpiration(expiredAt())
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    private Date expiredAt() {
        LocalDateTime now = LocalDateTime.now();
        return Date.from(now.plusHours(EXPIRED_DURATION).atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date issuedAt() {
        LocalDateTime now = LocalDateTime.now();
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(GRANT_TYPE)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public UUID validateTokenAndGetId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id", UUID.class);
    }

    public Authentication createAuthentication(UUID id) {
        UserDetails userDetails = userDetailService.loadUserByUsername(id.toString());
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
