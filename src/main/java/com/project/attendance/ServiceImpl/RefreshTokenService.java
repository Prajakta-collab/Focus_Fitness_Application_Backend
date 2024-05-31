package com.project.attendance.ServiceImpl;

import com.project.attendance.Exception.InternalServerException;
import com.project.attendance.Exception.ResourceNotFoundException;
import com.project.attendance.Model.RefreshToken;
import com.project.attendance.Model.User;
import com.project.attendance.Repository.RefreshTokenRepository;
import com.project.attendance.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;

    public RefreshToken createRefreshToken(String email){

        try{
            User userInfoExtracted = userRepository.findByEmail(email)
                    .orElseThrow(()-> new ResourceNotFoundException("email" , "email" + email ,  0));;


            RefreshToken refreshToken = RefreshToken.builder()
                    .user(userInfoExtracted)
                    .token(UUID.randomUUID().toString())
                    .expiryDate(Instant.now().plusMillis(864000000))
                    .build();

            return refreshTokenRepository.save(refreshToken);
        }catch (Exception ex) {
            throw new InternalServerException(ex.getMessage());
        }


    }

    public Optional<RefreshToken> findByToken(String token){
        try{
            return refreshTokenRepository.findByToken(token);
        }catch (Exception ex) {
            throw new InternalServerException("Internal Server Error");
        }

    }

    public void verifyExpiration(RefreshToken token){

        try{

            if(token.getExpiryDate().isBefore(Instant.now())){
                refreshTokenRepository.delete(token);
                throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
            }

        }catch (Exception ex) {
            throw new InternalServerException("Internal Server Error");
        }

    }

}