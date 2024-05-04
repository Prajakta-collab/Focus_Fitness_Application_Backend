package com.project.attendance.Repository;

import com.project.attendance.Model.Attendance;
import com.project.attendance.Model.Batch;
import com.project.attendance.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    public List<Attendance> findByUser(User user) ;
    public List<Attendance> findByBatchAndPresentDate(Batch batch , LocalDate presentDate) ;
    public Attendance findByUserAndPresentDate(User user , LocalDate presentDate) ;
}
