package com.pragma.plazacomida.infrastructure.configuration;

import com.pragma.plazacomida.domain.api.IUserServicePort;
import com.pragma.plazacomida.domain.model.UserModel;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final IUserServicePort userServicePort;

    public JwtAuthenticationFilter(JwtService jwtService, IUserServicePort userServicePort) {
        this.jwtService = jwtService;
        this.userServicePort = userServicePort;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        
        log.info("🔍 JWT Filter - Processing request: {} {}", request.getMethod(), request.getRequestURI());
        
        final String authHeader = request.getHeader("Authorization");
        log.info("🔍 JWT Filter - Authorization header: {}", authHeader);
        
        final String jwt;
        final String userEmail;
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("⚠️ JWT Filter - No Bearer token found or invalid format");
            filterChain.doFilter(request, response);
            return;
        }
        
        jwt = authHeader.substring(7);
        log.info("🔍 JWT Filter - Extracted JWT: {}", jwt.substring(0, Math.min(jwt.length(), 50)) + "...");
        
        userEmail = jwtService.extractUsername(jwt);
        log.info("🔍 JWT Filter - Extracted email: {}", userEmail);
        
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.info("🔍 JWT Filter - Looking up user by email: {}", userEmail);
            Optional<UserModel> userOpt = userServicePort.getUserByEmail(userEmail);
            
            if (userOpt.isPresent()) {
                log.info("✅ JWT Filter - User found: {}", userOpt.get().getEmail());
                UserDetails userDetails = createUserDetails(userOpt.get());
                
                boolean isTokenValid = jwtService.isTokenValid(jwt, userDetails);
                log.info("🔍 JWT Filter - Token valid: {}", isTokenValid);
                
                if (isTokenValid) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.info("✅ JWT Filter - Authentication set successfully for user: {}", userEmail);
                } else {
                    log.error("❌ JWT Filter - Token validation failed for user: {}", userEmail);
                }
            } else {
                log.error("❌ JWT Filter - User not found for email: {}", userEmail);
            }
        } else {
            if (userEmail == null) {
                log.error("❌ JWT Filter - Could not extract email from token");
            } else {
                log.info("ℹ️ JWT Filter - User already authenticated: {}", userEmail);
            }
        }
        
        log.info("🔍 JWT Filter - Continuing filter chain");
        filterChain.doFilter(request, response);
    }
    
    private UserDetails createUserDetails(UserModel user) {
        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getEmail())
            .password(user.getPassword())
            .authorities("ROLE_" + user.getRole().getName().toUpperCase())
            .build();
    }
} 