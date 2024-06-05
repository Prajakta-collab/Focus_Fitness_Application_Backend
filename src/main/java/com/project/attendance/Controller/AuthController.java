package com.project.attendance.Controller;

import com.project.attendance.Payload.Requests.JwtAuthRequest;
import com.project.attendance.Payload.Response.JwtAuthResponse;
import com.project.attendance.Payload.Requests.RefreshTokenRequestDTO;
import com.project.attendance.Payload.DTO.UserDTO;
import com.project.attendance.Payload.Response.RefreshTokenResponse;
import com.project.attendance.ServiceImpl.AuthServiceImpl;
import com.project.attendance.ServiceImpl.RefreshTokenService;
import com.project.attendance.ServiceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    AuthServiceImpl authService ;

    @Autowired
    UserServiceImpl userService ;

    @Autowired
    RefreshTokenService refreshTokenService ;

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

    @PostMapping("/by-email")
    public ResponseEntity<RefreshTokenResponse> getRefreshTokenByEmail(@RequestHeader("email") String email) {
        RefreshTokenResponse refreshTokenResponse = refreshTokenService.getRefreshTokenByEmail(email);
        return new ResponseEntity<>(refreshTokenResponse, HttpStatus.OK) ;
    }

}
