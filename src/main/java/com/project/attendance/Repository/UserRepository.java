package com.project.attendance.Repository;

import com.project.attendance.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User , Integer> {
    public List<User> findByShift(String shift) ;
    public Optional<User> findByEmail(String email) ;
}
