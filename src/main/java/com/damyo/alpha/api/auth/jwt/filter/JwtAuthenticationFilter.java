package com.damyo.alpha.api.auth.jwt.filter;

import com.damyo.alpha.api.auth.exception.AuthException;
import com.damyo.alpha.api.auth.exception.TokenException;
import com.damyo.alpha.api.auth.jwt.JwtProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

import static com.damyo.alpha.api.auth.exception.AuthErrorCode.*;


@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtProvider.resolveToken(request);
        if (StringUtils.hasText(token)) {
            try {
                String id = jwtProvider.validateTokenAndGetId(token);
                Authentication authentication = jwtProvider.createAuthentication(UUID.fromString(id));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (ExpiredJwtException e) {
                request.setAttribute("exception", EXPIRED_TOKEN);
            } catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
                request.setAttribute("exception", INVALID_TOKEN);
            } catch (Exception e) {
                request.setAttribute("exception", UNKNOWN_ERROR);
            }
        }
        filterChain.doFilter(request, response);
    }
}
