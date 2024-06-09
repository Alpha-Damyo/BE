package com.damyo.alpha.api.auth.jwt;

import com.damyo.alpha.api.auth.exception.AuthErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.damyo.alpha.api.auth.exception.AuthErrorCode.*;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        AuthErrorCode errorCode = (AuthErrorCode) request.getAttribute("exception");
        if (errorCode.equals(EXPIRED_TOKEN)) {
            setResponse(response, EXPIRED_TOKEN);
        } else if (errorCode.equals(INVALID_TOKEN)) {
            setResponse(response, INVALID_TOKEN);
        } else {
            log.info("unknown error message: " + errorCode.getMessage());
            setResponse(response, UNKNOWN_ERROR);
        }
    }

    private void setResponse(HttpServletResponse response, AuthErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorCode.getHttpStatus().value());

        JSONObject responseJson = new JSONObject();
        responseJson.put("code", errorCode.getExceptionCode());
        responseJson.put("message", errorCode.getMessage());

        response.getWriter().print(responseJson);
    }
}
