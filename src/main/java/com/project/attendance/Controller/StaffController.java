package com.project.attendance.Controller;

import com.project.attendance.Payload.DTO.UserDTO;
import com.project.attendance.ServiceImpl.StaffServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/staff")
public class StaffController {

    @Autowired
    StaffServiceImpl staffService ;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> createStaff(@RequestBody UserDTO userDTO){
        UserDTO createdUser = staffService.createStaff(userDTO) ;
        return ResponseEntity.ok(createdUser) ;
    }

}
