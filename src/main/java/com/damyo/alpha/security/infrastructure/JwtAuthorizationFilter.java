package com.damyo.alpha.security.infrastructure;

import com.damyo.alpha.exception.exception.AuthException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

import static com.damyo.alpha.exception.errorCode.AuthErrorCode.*;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    @Value("${jwt.allowed-urls}")
    private String[] allowedUrls;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtProvider.resolveToken(request);

        try {
            String email = jwtProvider.validateTokenAndGetEmail(token);
            Authentication authentication = jwtProvider.createAuthentication(email);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (SecurityException e) {
            request.setAttribute("exception", new AuthException(SIGNATURE_NOT_FOUND));
        } catch (SignatureException e) {
            request.setAttribute("exception", new AuthException(SIGNATURE_INVALID));
        } catch (MalformedJwtException e) {
            request.setAttribute("exception", new AuthException(MALFORMED_TOKEN));
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", new AuthException(EXPIRED_TOKEN));
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", new AuthException(UNSUPPORTED_TOKEN));
        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", new AuthException(INVALID_TOKEN));
        }

        filterChain.doFilter(request, response);
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return Arrays.stream(allowedUrls).anyMatch(path::startsWith);
    }
}
