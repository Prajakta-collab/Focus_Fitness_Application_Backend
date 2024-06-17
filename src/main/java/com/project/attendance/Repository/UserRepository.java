package com.project.attendance.Repository;

import com.project.attendance.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByShift(String shift);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE u.end_LocalDate < CURRENT_DATE AND r.roleName NOT IN ('ROLE_STAFF', 'ROLE_ADMIN')")
    List<User> getExpiredSubscriptionsUsers();

    @Query("SELECT u FROM User u JOIN u.roles r WHERE u.end_LocalDate = CURRENT_DATE AND r.roleName NOT IN ('ROLE_STAFF', 'ROLE_ADMIN')")
    List<User> findUsersWithSubscriptionEndingToday();

    @Query("SELECT u FROM User u JOIN u.roles r WHERE u.end_LocalDate BETWEEN :startDate AND :endDate AND r.roleName NOT IN ('ROLE_STAFF', 'ROLE_ADMIN')")
    List<User> findUsersWithExpiringSubscriptions(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.roleName NOT IN ('ROLE_STAFF', 'ROLE_ADMIN')")
    List<User> findNormalUsers();
}
