package com.project.attendance.ServiceImpl;

import com.project.attendance.Exception.InternalServerException;
import com.project.attendance.Exception.ResourceNotFoundException;
import com.project.attendance.Model.OTP;
import com.project.attendance.Model.RefreshToken;
import com.project.attendance.Model.User;
import com.project.attendance.Payload.DTO.UserDTO;
import com.project.attendance.Payload.Requests.EmailRequest;
import com.project.attendance.Payload.Requests.JwtAuthRequest;
import com.project.attendance.Payload.Response.ApiResponse;
import com.project.attendance.Payload.Response.JwtAuthResponse;
import com.project.attendance.Payload.Requests.RefreshTokenRequestDTO;
import com.project.attendance.Payload.Response.RefreshTokenResponse;
import com.project.attendance.Repository.OTPRepository;
import com.project.attendance.Utilities.Utility;
import com.project.attendance.security.JwtTokenHelper;
import jakarta.transaction.Transactional;
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

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.security.core.context.SecurityContextHolder.*;

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

    @Autowired
    EmailService emailService ;

    @Autowired
    Utility utility ;

    @Autowired
    OTPRepository otpRepository ;

    public JwtAuthResponse login(JwtAuthRequest request){

        this.doAuthenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        String loggedInUserType = getAuthoritiesString(userDetails.getAuthorities());
        if(!loggedInUserType.equals(request.getUsertype())){
            throw new BadCredentialsException("You are not "+ request.getUsertype()+ " Please provide details accordingly") ;
        }


        String token = this.helper.generateToken(userDetails);

        /* Refresh Token - If user is log in successfully  */
        RefreshToken refreshToken =  refreshTokenService.createRefreshToken(request.getUsername()) ;

        JwtAuthResponse response = new JwtAuthResponse() ;
        response.setToken(token) ;
        response.setSuccess(Boolean.TRUE);
        response.setRefreshToken(refreshToken.getToken());

        return response;
    }


    @Transactional
    public ApiResponse logout(Integer userId) {
        // Clear SecurityContext
        SecurityContextHolder.clearContext();

        // Remove refresh token associated with the user
        refreshTokenService.removeRefreshToken(userId);

        return ApiResponse.builder()
                .message("Logout successful")
                .success(Boolean.TRUE)
                .className(this.getClass().getSimpleName())
                .build();
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
                .success(Boolean.TRUE)
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

    public static String getAuthoritiesString(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }


    public OTP sendForgetPasswordOTPByEmail(String email){

        String otp = utility.generateOTP();

        //save otp in DB
        OTP generatedOTP = OTP.builder()
                .otp(otp)
                .email(email)
                .creationTime(LocalDateTime.now())
                .build();

        OTP savedOTP = otpRepository.save(generatedOTP) ;

        EmailRequest emailRequest = EmailRequest.builder()
                .recipient(email)
                .subject("Password Reset Code - FOCUS FITNESS")
                .body("Your password reset code is " + otp)
                .build() ;

        emailService.sendEmail(emailRequest);
        return savedOTP ;
    }

    public OTP getVerificationOTPByEmail(String email){

        String otp = utility.generateOTP();

        //save otp in DB
        OTP generatedOTP = OTP.builder()
                .otp(otp)
                .email(email)
                .creationTime(LocalDateTime.now())
                .build() ;

        OTP savedOTP = otpRepository.save(generatedOTP) ;

        EmailRequest emailRequest = EmailRequest.builder()
                .recipient(email)
                .subject("Email Verification Code - FOCUS FITNESS")
                .body("Your email verification code is " + otp)
                .build() ;

        emailService.sendEmail(emailRequest);
        return savedOTP ;
    }


}
