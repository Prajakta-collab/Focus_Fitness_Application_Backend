package com.project.attendance.ServiceImpl;

import com.project.attendance.Exception.InternalServerException;
import com.project.attendance.Exception.ResourceExpireException;
import com.project.attendance.Exception.ResourceNotFoundException;
import com.project.attendance.Model.RefreshToken;
import com.project.attendance.Model.User;
import com.project.attendance.Payload.Response.RefreshTokenResponse;
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
        User userInfoExtracted = userRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("email" , "email" + email ,  0));;


        RefreshToken refreshToken = RefreshToken.builder()
                .user(userInfoExtracted)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(864000000))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }


    public RefreshTokenResponse getRefreshTokenById(Integer userId) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId);

        if(refreshToken != null){
            return RefreshTokenResponse.builder()
                    .refreshToken(refreshToken)
                    .success(Boolean.TRUE)
                    .build() ;
        }else{
            return RefreshTokenResponse.builder()
                    .refreshToken(null)
                    .success(Boolean.FALSE)
                    .build() ;
        }
    }

    public RefreshTokenResponse getRefreshTokenByEmail(String email) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserEmail(email);

        if(refreshToken != null){
            return RefreshTokenResponse.builder()
                    .refreshToken(refreshToken)
                            .success(Boolean.TRUE)
                                    .build() ;
        }else{
            return RefreshTokenResponse.builder()
                    .refreshToken(null)
                    .success(Boolean.FALSE)
                    .build() ;
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
        if(token.getExpiryDate().isBefore(Instant.now())){
            refreshTokenRepository.delete(token);
            throw new ResourceExpireException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
    }

    public void removeRefreshToken(Integer userId){
        refreshTokenRepository.deleteByUserId(userId);
    }

}