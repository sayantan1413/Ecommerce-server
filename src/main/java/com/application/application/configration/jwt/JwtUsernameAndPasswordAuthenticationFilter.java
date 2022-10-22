package com.application.application.configration.jwt;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.application.application.configration.jwt.jwtConfig.JwtConfig;
import com.application.application.dto.LoginRequest;
import com.application.application.model.AppUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private JwtUtil jwtUtil;
    private final SecretKey secretKey;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager,
            JwtConfig jwtConfig, SecretKey secretKey, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {

            LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword());

            Authentication authenticate = authenticationManager.authenticate(authentication);
            logger.info("login attentication completed.");
            return authenticate;
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        Date expiry = java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays()));
        String token = jwtUtil.generateJwtToken(authResult, secretKey, expiry);
        response.setContentType("application/json");
        Map<String, String> details = new HashMap<>();
        details.put(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);
        details.put("email",
                ((AppUserDetails) authResult.getPrincipal()).getEmail());
        new ObjectMapper().writeValue(response.getOutputStream(), details);
        logger.info("success in login.");
    }

}
