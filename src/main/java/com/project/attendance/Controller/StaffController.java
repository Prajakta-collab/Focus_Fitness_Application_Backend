package com.project.attendance.Controller;

import com.project.attendance.Model.User;
import com.project.attendance.Payload.DTO.UserDTO;
import com.project.attendance.Payload.Response.ApiResponse;
import com.project.attendance.ServiceImpl.StaffServiceImpl;
import com.project.attendance.ServiceImpl.UserServiceImpl;
import com.project.attendance.Utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/staff")
public class StaffController {

    @Autowired
    Utility utility ;

    @Autowired
    StaffServiceImpl staffService ;

    @Autowired
    UserServiceImpl userService ;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> createStaff(@RequestBody UserDTO userDTO){
        UserDTO createdUser = staffService.createStaff(userDTO) ;
        return ResponseEntity.ok(createdUser) ;
    }

    @PreAuthorize("hasRole('ROLE_STAFF')")
    @PostMapping("/assign-trainee")
    public ApiResponse assignTraineeToTrainer(@RequestHeader Integer traineeId,
                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String token

    ) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String username = authentication.getName() ;

        User loggedInUser = userService.getUserByEmail(username) ;

        //Validating user
        if(!loggedInUser.getRoles().get(0).getRoleName().equals("ROLE_STAFF")){
            throw new AccessDeniedException("Unauthorized access to data") ;
        }

        User trainer = userService.getUserById(loggedInUser.getId());
        User trainee = userService.getUserById(traineeId);
        return staffService.assignTraineeToTrainer(trainer, trainee);
    }

    @PreAuthorize("hasRole('ROLE_STAFF')")
    @GetMapping("/trainees")
    public List<User> getTrainees(@RequestHeader(HttpHeaders.AUTHORIZATION) String token
    ) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String username = authentication.getName() ;

        User loggedInUser = userService.getUserByEmail(username) ;

        //Validating user
        if(!loggedInUser.getRoles().get(0).getRoleName().equals("ROLE_STAFF")){
            throw new AccessDeniedException("Unauthorized access to data") ;
        }

        User trainer = userService.getUserById(loggedInUser.getId());
        return staffService.getTrainees(trainer);
    }

    @GetMapping("/trainers")
    public List<User> getTrainers(@PathVariable Integer traineeId) {
        User trainee = userService.getUserById(traineeId);
        return staffService.getTrainers(trainee);
    }
}
