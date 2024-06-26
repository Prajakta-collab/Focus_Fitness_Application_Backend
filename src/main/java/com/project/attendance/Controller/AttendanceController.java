package com.project.attendance.Controller;

import com.project.attendance.Model.User;
import com.project.attendance.Payload.Response.AttendanceResponse;
import com.project.attendance.ServiceImpl.UserServiceImpl;
import com.project.attendance.Utilities.Utility;
import com.project.attendance.Payload.Response.ApiResponse;
import com.project.attendance.Payload.DTO.AttendanceDTO;
import com.project.attendance.Payload.DTO.UserDTO;
import com.project.attendance.ServiceImpl.AttendanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceController {

    @Autowired
    AttendanceServiceImpl attendanceService ;

    @Autowired
    UserServiceImpl userService ;

    @Autowired
    Utility utility ;

    @PostMapping("")
    public ResponseEntity<ApiResponse> markAttendance(
                                                      @RequestParam Integer batchId,
                                                      @RequestHeader Integer userId ,
                                                      @RequestHeader(HttpHeaders.AUTHORIZATION) String token){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String email = authentication.getName() ;

        User loggedInUser = userService.getUserByEmail(email) ;

        System.out.println("before");
        //Validating user
        utility.validateUser(token , email , authorities , userId , loggedInUser);
        System.out.println("After");

        AttendanceDTO attendanceDTO = attendanceService.markAttendance(userId , batchId) ;

        ApiResponse apiResponse = new ApiResponse() ;
        apiResponse.setMessage("Attendance Marked Successfully for UserID :- "+ userId);
        apiResponse.setSuccess(Boolean.TRUE);

        return ResponseEntity.ok(apiResponse) ;
    }

    @GetMapping("")
    public ResponseEntity<AttendanceResponse> getAllPresentDays(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String username = authentication.getName() ;

        User loggedInUser = userService.getUserByEmail(username) ;

        //Validating user
        utility.validateUser(token , username , authorities , loggedInUser.getId() , loggedInUser);
        AttendanceResponse res = attendanceService.getAllPresentDays(loggedInUser.getId()) ;
        return ResponseEntity.ok(res) ;
    }

    @GetMapping("/batch")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getAllPresentUserByBatch(@RequestParam Integer batchId ,
                                                                  @RequestParam String date){
        List<User> users = attendanceService.getAllPresentUserByBatch(batchId , date) ;
        return ResponseEntity.ok(users) ;
    }
}
