package com.project.attendance.Controller;

import com.project.attendance.Exception.ResourceNotFoundException;
import com.project.attendance.Model.OTP;
import com.project.attendance.Model.User;
import com.project.attendance.Payload.Requests.EmailRequest;
import com.project.attendance.Payload.Requests.ForgetPasswordPayload;
import com.project.attendance.Payload.Requests.JwtAuthRequest;
import com.project.attendance.Payload.Response.ApiResponse;
import com.project.attendance.Payload.Response.JwtAuthResponse;
import com.project.attendance.Payload.Requests.RefreshTokenRequestDTO;
import com.project.attendance.Payload.DTO.UserDTO;
import com.project.attendance.Payload.Response.RefreshTokenResponse;
import com.project.attendance.Repository.UserRepository;
import com.project.attendance.ServiceImpl.AuthServiceImpl;
import com.project.attendance.ServiceImpl.EmailService;
import com.project.attendance.ServiceImpl.RefreshTokenService;
import com.project.attendance.ServiceImpl.UserServiceImpl;
import com.project.attendance.Utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    AuthServiceImpl authService ;

    @Autowired
    PasswordEncoder passwordEncoder ;

    @Autowired
    UserRepository userRepository ;

    @Autowired
    UserServiceImpl userService ;

    @Autowired
    RefreshTokenService refreshTokenService ;

    @Autowired
    EmailService emailService ;

    @Autowired
    Utility utility ;

    @PostMapping("/signup")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_STAFF')")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO ,
                                              @RequestHeader String otp
    ){
        UserDTO createdUser = userService.createUser(userDTO , otp) ;
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

    @PostMapping("/signup-otp/send-email")
    public ResponseEntity<ApiResponse> sendEmail(@RequestHeader String email) {

        OTP savedOTP = authService.getVerificationOTPByEmail(email);

        ApiResponse apiResponse = ApiResponse.builder()
                .message("Email sent successfully")
                .success(Boolean.TRUE)
                .className(String.valueOf(this.getClass()))
                .build();

        return ResponseEntity.ok(apiResponse) ;
    }

    @PostMapping("/forget-password/send-email")
    public ResponseEntity<ApiResponse> sendForgetPasswordOTPByEmail(@RequestHeader String email) {

        OTP savedOTP = authService.sendForgetPasswordOTPByEmail(email);

        ApiResponse apiResponse = ApiResponse.builder()
                .message("Email sent successfully")
                .success(Boolean.TRUE)
                .className(String.valueOf(this.getClass()))
                .build();

        return ResponseEntity.ok(apiResponse) ;
    }

    @PostMapping("/forget-password/verification")
    public ResponseEntity<ApiResponse> verifyForgetPasswordOTP(@RequestBody ForgetPasswordPayload forgetPasswordPayload) {


        utility.verifyOtp(forgetPasswordPayload.getEmail() , forgetPasswordPayload.getOtp());

        //password check
        String password = forgetPasswordPayload.getPassword() ;
        String confirmPassword = forgetPasswordPayload.getConfirmPassword() ;

        if(!password.equals(confirmPassword)){
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Password and Confirm Password are not equal")
                    .success(Boolean.FALSE)
                    .className(String.valueOf(this.getClass()))
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse) ;
        }

        User user = userRepository.findByEmail(forgetPasswordPayload.getEmail())
                .orElseThrow(()-> new ResourceNotFoundException("User" , "email" + forgetPasswordPayload.getEmail() , 0));

        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user) ;

        ApiResponse apiResponse = ApiResponse.builder()
                .message("Password reset successfully")
                .success(Boolean.TRUE)
                .className(String.valueOf(this.getClass()))
                .build();

        return ResponseEntity.ok(apiResponse) ;
    }

}
