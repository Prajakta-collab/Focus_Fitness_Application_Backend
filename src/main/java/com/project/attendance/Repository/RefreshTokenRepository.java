package com.project.attendance.Repository;

import com.project.attendance.Model.RefreshToken;
import com.project.attendance.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken , Integer> {
    Optional<RefreshToken> findByToken(String token) ;
//    User findByEmailAndPassword(String email , String password) ;
}
