package com.project.attendance.Controller;

import com.project.attendance.Exception.ResourceNotFoundException;
import com.project.attendance.Model.RefreshToken;
import com.project.attendance.Model.User;
import com.project.attendance.Payload.JwtAuthRequest;
import com.project.attendance.Payload.JwtAuthResponse;
import com.project.attendance.Payload.RefreshTokenRequestDTO;
import com.project.attendance.Payload.UserDTO;
import com.project.attendance.ServiceImpl.AuthServiceImpl;
import com.project.attendance.ServiceImpl.RefreshTokenService;
import com.project.attendance.ServiceImpl.UserServiceImpl;
import com.project.attendance.security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    AuthServiceImpl authService ;

    @Autowired
    UserServiceImpl userService ;

    @PostMapping("/signup")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_STAFF')")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        UserDTO createdUser = userService.createUser(userDTO) ;
        return ResponseEntity.ok(createdUser) ;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) {
        JwtAuthResponse res = authService.login(request) ;
        return new ResponseEntity<>(res, HttpStatus.OK) ;
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<JwtAuthResponse> refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        JwtAuthResponse res = authService.createAccessToken(refreshTokenRequestDTO) ;
        return new ResponseEntity<>(res, HttpStatus.OK) ;
    }

}
