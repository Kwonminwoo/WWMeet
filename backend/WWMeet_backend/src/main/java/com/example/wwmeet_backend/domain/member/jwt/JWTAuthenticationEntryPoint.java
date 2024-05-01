package com.example.wwmeet_backend.domain.member.jwt;

import com.example.wwmeet_backend.global.exception.ErrorCode;
import com.example.wwmeet_backend.global.response.ResponseAPI;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import jdk.jfr.ContentType;
import lombok.RequiredArgsConstructor;
import org.h2.util.json.JSONObject;
import org.hibernate.internal.util.StringHelper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@RequiredArgsConstructor
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {
        String exceptionType = (String) request.getAttribute("exceptionType");

        if(ErrorCode.LOGIN_INFO_EXPIRED.getMessage().equals(exceptionType)){
            response.setStatus(HttpStatus.FORBIDDEN.value());
            ResponseAPI res = ResponseAPI.response(
                ErrorCode.LOGIN_INFO_NOT_MATCH.getMessage());
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(objectMapper.writeValueAsString(res));
        }
    }
}
