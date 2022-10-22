package com.application.application.configration.jwt;

import java.io.IOException;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.application.application.configration.jwt.jwtConfig.JwtConfig;
import com.application.application.services.Implementation.UserDetailsServiceImpl;

public class JwtTokenVerifier extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsService;

    private JwtUtil jwtUtil;
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenVerifier.class);
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    public JwtTokenVerifier(SecretKey secretKey,
            JwtConfig jwtConfig, JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtil.validateJwtToken(jwt, secretKey)) {
                String username = jwtUtil.getUserNameFromJwtToken(jwt, secretKey);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                System.out.println(userDetails.getUsername());
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("API filteration completed.");
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
            System.out.println(e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(jwtConfig.getAuthorizationHeader());

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(jwtConfig.getTokenPrefix())) {
            return headerAuth.replace(jwtConfig.getTokenPrefix(), "");
        }

        return null;
    }

}
