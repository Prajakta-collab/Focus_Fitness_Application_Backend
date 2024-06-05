package com.project.attendance.Repository;

import com.project.attendance.Model.RefreshToken;
import com.project.attendance.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken , Integer> {
    Optional<RefreshToken> findByToken(String token) ;

    @Query("SELECT rt FROM RefreshToken rt WHERE rt.user.email = :email")
    RefreshToken findByUserEmail(@Param("email") String email);
}
