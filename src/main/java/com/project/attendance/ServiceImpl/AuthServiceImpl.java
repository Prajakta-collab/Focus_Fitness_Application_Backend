package com.project.attendance.ServiceImpl;

import com.project.attendance.Exception.ResourceNotFoundException;
import com.project.attendance.Model.RefreshToken;
import com.project.attendance.Model.User;
import com.project.attendance.Payload.JwtAuthRequest;
import com.project.attendance.Payload.JwtAuthResponse;
import com.project.attendance.Payload.RefreshTokenRequestDTO;
import com.project.attendance.security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl {

    @Autowired
    private RefreshTokenService refreshTokenService ;

    @Autowired
    private AuthenticationManager manager ;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper helper ;


    public JwtAuthResponse login(JwtAuthRequest request){

        this.doAuthenticate(request.getUsername(), request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.helper.generateToken(userDetails);

        /* Refresh Token - If user is log in successfully  */
        RefreshToken refreshToken =  refreshTokenService.createRefreshToken(request.getUsername()) ;


        JwtAuthResponse response = new JwtAuthResponse() ;
        response.setToken(token) ;
        response.setRefreshToken(refreshToken.getToken());

        return response;
    }

    public JwtAuthResponse createAccessToken(RefreshTokenRequestDTO refreshTokenRequestDTO){
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
                .refreshToken(refreshTokenRequestDTO.getToken())
                .build();
    }

    public void doAuthenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);

        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
    }
}
