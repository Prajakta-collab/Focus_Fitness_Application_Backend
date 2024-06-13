package com.project.attendance.Repository;

import com.project.attendance.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User , Integer> {
    public List<User> findByShift(String shift) ;
    public Optional<User> findByEmail(String email) ;

    @Query("SELECT COUNT(u) FROM User u WHERE u.end_LocalDate < CURRENT_DATE")
    Integer countExpiredSubscriptions();

    @Query("SELECT COUNT(u) FROM User u WHERE u.end_LocalDate = CURRENT_DATE")
    Integer findUsersWithSubscriptionEndingToday();

    @Query("SELECT u FROM User u WHERE u.end_LocalDate BETWEEN :startDate AND :endDate")
    List<User> findUsersWithExpiringSubscriptions(LocalDate startDate, LocalDate endDate);
}

