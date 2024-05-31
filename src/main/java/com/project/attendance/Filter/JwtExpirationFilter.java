package com.project.attendance.Filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.attendance.Payload.Response.ApiResponse;
import com.project.attendance.security.JwtTokenHelper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtExpirationFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenHelper jwtTokenHelper ;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestToken = request.getHeader("Authorization");

        if (requestToken != null && requestToken.startsWith("Bearer")) {
            String token = requestToken.substring(7);

            try {
                if (jwtTokenHelper.isTokenExpired(token)) {
                    handleExpiredTokenResponse(response , token);
                    return;
                }
            } catch (ExpiredJwtException ex) {
                handleExpiredTokenResponse(response , token);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }


    private void handleExpiredTokenResponse(HttpServletResponse response, String token) throws IOException {
        ApiResponse apiResponse = new ApiResponse("JWT token has expired", Boolean.FALSE , this.getClass().getSimpleName());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(apiResponse);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }
}
