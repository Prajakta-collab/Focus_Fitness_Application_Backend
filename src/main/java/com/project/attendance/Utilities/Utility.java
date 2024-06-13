package com.project.attendance.Utilities;

import com.project.attendance.Config.AppConstants;
import com.project.attendance.Exception.ResourceNotFoundException;
import com.project.attendance.Model.OTP;
import com.project.attendance.Model.User;
import com.project.attendance.Repository.OTPRepository;
import com.project.attendance.Repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Random;


@Configuration
public class Utility {

    @Autowired
    UserRepository userRepository ;

    @Autowired
    OTPRepository otpRepository ;

    public void validateUser(String token , String email , Collection<? extends GrantedAuthority> authorities , Integer userId , User loggedInUser) {

        String actualToken = token.substring(7); // Remove "Bearer " prefix
        String username = Jwts.parser().setSigningKey(String.valueOf(AppConstants.JWT_SECRET)).parseClaimsJws(actualToken).getBody().getSubject();


        /* Admin can access anything so returning same id to show same person is accessing */
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_ADMIN") || authority.getAuthority().equals("ROLE_STAFF")) {
                return;
            }
        }

        if(!Objects.equals(userId, loggedInUser.getId())){
            System.out.println("Check :- userId" + userId + " --  loggedInUser" + loggedInUser.getId() );
            throw new AccessDeniedException("Unauthorized access to data") ;
        }

        if(!username.equals(email)){
            System.out.println("Check :- token user" + username + " --  owner" + email );
            throw new AccessDeniedException("Unauthorized access to data") ;
        }
    }

    public String generateOTP(){
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }

    public void verifyOtp(String email, String otp) {
        OTP otpEntity = otpRepository.findLastOtpByEmail(email);

        if(otpEntity != null && otpEntity.getOtp().equals(otp)){
            return ;
        }

        throw new BadCredentialsException("You have entered invalid otp") ;
    }
}
