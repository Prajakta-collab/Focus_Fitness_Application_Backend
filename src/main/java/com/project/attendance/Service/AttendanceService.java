package com.project.attendance.Service;

import com.project.attendance.Payload.DTO.AttendanceDTO;
import com.project.attendance.Payload.DTO.UserDTO;
import com.project.attendance.Payload.Response.AttendanceResponse;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    public AttendanceDTO markAttendance(Integer userId , Integer batchId) ;
    public AttendanceResponse getAllPresentDays(Integer userId) ;
    public List<UserDTO> getAllPresentUserByBatch(Integer batchId , String date) ;
}
