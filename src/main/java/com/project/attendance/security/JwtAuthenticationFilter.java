package com.project.attendance.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService ;

    @Autowired
    private JwtTokenHelper jwtTokenHelper ;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        /*step - 1*/
        // get token  - (it will give = Bearer + token)
        String requestToken = request.getHeader("authorization") ;

        String username = null ;
        String token = null ;

        if(requestToken != null && requestToken.startsWith("Bearer")){
            token = requestToken.substring(7) ;

            try{
                username = jwtTokenHelper.getUsernameFromToken(token) ;
            }catch (IllegalArgumentException e){
                System.out.println("Unable to get Jwt token");
            }catch (ExpiredJwtException e){
                System.out.println("JWT token has expired");
            }catch (MalformedJwtException e){
                System.out.println("Invalid JWT Exception");
            }
        }else{
            System.out.println("JWT token does not start with bearer");
        }


        /*step - 2*/
        // Once we get token , now validate

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username) ;

            if(jwtTokenHelper.validateToken(token , userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails , null ,userDetails.getAuthorities()) ;
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }else {
                System.out.println("Invalid JWT Token");
            }

        }else{
            System.out.println("Username is null or Authentication context is not null");
        }

        filterChain.doFilter(request , response);
    }
}
