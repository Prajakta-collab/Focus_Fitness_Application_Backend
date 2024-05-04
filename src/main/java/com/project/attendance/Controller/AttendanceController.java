package com.project.attendance.Controller;

import com.project.attendance.Payload.AttendanceDTO;
import com.project.attendance.Payload.UserDTO;
import com.project.attendance.ServiceImpl.AttendanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceController {

    @Autowired
    AttendanceServiceImpl attendanceService ;

    @PostMapping("")
    public ResponseEntity<AttendanceDTO> markAttendance(@RequestParam Integer userId ,
                                                        @RequestParam Integer batchId){

        AttendanceDTO attendanceDTO = attendanceService.markAttendance(userId , batchId) ;
        return ResponseEntity.ok(attendanceDTO) ;
    }

    @GetMapping("")
    public ResponseEntity<List<LocalDate>> getAllPresentDays(@RequestParam Integer userId){
        List<LocalDate> presentDates = attendanceService.getAllPresentDays(userId) ;
        return ResponseEntity.ok(presentDates) ;
    }

    @GetMapping("/batch")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllPresentUserByBatch(@RequestParam Integer batchId ,
                                                                  @RequestParam String date){
        List<UserDTO> users = attendanceService.getAllPresentUserByBatch(batchId , date) ;
        return ResponseEntity.ok(users) ;
    }
}
