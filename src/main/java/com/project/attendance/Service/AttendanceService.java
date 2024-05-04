package com.project.attendance.Service;

import com.project.attendance.Model.User;
import com.project.attendance.Payload.AttendanceDTO;
import com.project.attendance.Payload.UserDTO;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    public AttendanceDTO markAttendance(Integer userId , Integer batchId) ;
    public List<LocalDate> getAllPresentDays(Integer userId) ;
    public List<UserDTO> getAllPresentUserByBatch(Integer batchId , String date) ;
}
