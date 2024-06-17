package com.project.attendance.Repository;

import com.project.attendance.Model.PTSession;
import com.project.attendance.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PTSessionRepository extends JpaRepository<PTSession, Integer> {

    List<PTSession> findByTrainer(User trainer);
    List<PTSession> findByTrainee(User trainee);

    // Alternatively, you can use custom queries if needed
    @Query("SELECT p FROM PTSession p WHERE p.trainer = :trainer")
    List<PTSession> findPTSessionsByTrainer(@Param("trainer") User trainer);

    @Query("SELECT p FROM PTSession p WHERE p.trainee = :trainee")
    List<PTSession> findPTSessionsByTrainee(@Param("trainee") User trainee);
}
