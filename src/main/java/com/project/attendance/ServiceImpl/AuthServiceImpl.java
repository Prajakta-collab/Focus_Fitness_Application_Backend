package com.project.attendance.ServiceImpl;

import com.project.attendance.Exception.InternalServerException;
import com.project.attendance.Exception.ResourceNotFoundException;
import com.project.attendance.Model.RefreshToken;
import com.project.attendance.Model.User;
import com.project.attendance.Payload.DTO.UserDTO;
import com.project.attendance.Payload.Requests.JwtAuthRequest;
import com.project.attendance.Payload.Response.JwtAuthResponse;
import com.project.attendance.Payload.Requests.RefreshTokenRequestDTO;
import com.project.attendance.security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Service
public class AuthServiceImpl {

    @Autowired
    private RefreshTokenService refreshTokenService ;

    @Autowired
    private AuthenticationManager manager ;

    @Autowired
    private UserServiceImpl userService ;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper helper ;


    public JwtAuthResponse login(JwtAuthRequest request){

        try{

            this.doAuthenticate(request.getUsername(), request.getPassword());

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            String token = this.helper.generateToken(userDetails);

            /* Refresh Token - If user is log in successfully  */
            RefreshToken refreshToken =  refreshTokenService.createRefreshToken(request.getUsername()) ;

            JwtAuthResponse response = new JwtAuthResponse() ;
            response.setToken(token) ;
            response.setSuccess(Boolean.TRUE);
            response.setRefreshToken(refreshToken.getToken());

            return response;

        }catch (Exception ex) {
            throw new InternalServerException("Internal Server Error");
        }
    }

    public JwtAuthResponse createAccessToken(RefreshTokenRequestDTO refreshTokenRequestDTO){

        try{

            RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                    .orElseThrow(() -> new ResourceNotFoundException("RefreshToken Not found" , "" + refreshTokenRequestDTO.getToken() , 0)) ;

            // Check if expired then throw error
            refreshTokenService.verifyExpiration(refreshToken);

            User user = refreshToken.getUser();

            // Convert User entity to UserDetails
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    user.getAuthorities()
            );

            // Generate new access token
            String accessToken = helper.generateToken(userDetails);

            return JwtAuthResponse.builder()
                    .token(accessToken)
                    .success(Boolean.TRUE)
                    .refreshToken(refreshTokenRequestDTO.getToken())
                    .build();


        }catch (Exception ex) {
            throw new InternalServerException("Internal Server Error");
        }
    }

    public void doAuthenticate(String username, String password) {

        try{

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);

            try {
                manager.authenticate(authentication);
            } catch (BadCredentialsException e) {
                throw new BadCredentialsException(" Invalid Username or Password  !!");
            }
        }catch (Exception ex) {
            throw new InternalServerException("Internal Server Error");
        }

    }
}