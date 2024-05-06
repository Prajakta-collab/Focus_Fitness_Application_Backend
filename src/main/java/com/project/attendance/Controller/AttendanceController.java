package com.project.attendance.Controller;

import com.project.attendance.Config.AppConstants;
import com.project.attendance.Config.Utility;
import com.project.attendance.Payload.ApiResponse;
import com.project.attendance.Payload.AttendanceDTO;
import com.project.attendance.Payload.UserDTO;
import com.project.attendance.Repository.UserRepository;
import com.project.attendance.ServiceImpl.AttendanceServiceImpl;
import com.project.attendance.ServiceImpl.UserServiceImpl;
import com.project.attendance.security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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

    @Autowired
    Utility utility ;

    @PostMapping("")
    public ResponseEntity<ApiResponse> markAttendance(@RequestParam Integer userId ,
                                                      @RequestParam Integer batchId,
                                                      @RequestHeader(HttpHeaders.AUTHORIZATION) String token){

        utility.validateUser(token , userId);

        AttendanceDTO attendanceDTO = attendanceService.markAttendance(userId , batchId) ;

        ApiResponse apiResponse = new ApiResponse() ;
        apiResponse.setMessage("Attendance Marked Successfully for UserID :- "+ userId);
        apiResponse.setSuccess(Boolean.TRUE);

        return ResponseEntity.ok(apiResponse) ;
    }

    @GetMapping("")
    public ResponseEntity<List<LocalDate>> getAllPresentDays(@RequestParam Integer userId ,
                                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
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
