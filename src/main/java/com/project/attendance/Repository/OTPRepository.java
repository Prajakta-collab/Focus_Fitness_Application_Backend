package com.project.attendance.Repository;

import com.project.attendance.Model.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OTPRepository extends JpaRepository<OTP, Long> {

    @Query("SELECT o FROM OTP o WHERE o.email = :email AND o.creationTime = (SELECT MAX(o2.creationTime) FROM OTP o2 WHERE o2.email = :email)")
    OTP findLastOtpByEmail(@Param("email") String email);
}

