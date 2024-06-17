package com.project.attendance.Repository;

import com.project.attendance.Model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.user.id = :userId")
    void deleteByUserId(@Param("userId") Integer userId);

    @Query("SELECT rt FROM RefreshToken rt WHERE rt.user.id = :userId")
    RefreshToken findByUserId(@Param("userId") Integer userId);

    @Query("SELECT rt FROM RefreshToken rt WHERE rt.user.email = :email")
    RefreshToken findByUserEmail(@Param("email") String email);
}
