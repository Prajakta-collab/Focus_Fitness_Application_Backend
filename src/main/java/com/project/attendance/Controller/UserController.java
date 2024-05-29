package com.project.attendance.Controller;

import com.project.attendance.Utilities.Utility;
import com.project.attendance.Payload.DTO.UserDTO;
import com.project.attendance.ServiceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserServiceImpl userService ;

    @Autowired
    Utility utility ;

    @GetMapping("/all_users")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_STAFF')")
    public ResponseEntity<List<UserDTO>> getAllUser(){
        List<UserDTO> allUsers = userService.getAllUser() ;
        return ResponseEntity.ok(allUsers) ;
    }

    @GetMapping()
    public ResponseEntity<UserDTO> getUserById(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String username = authentication.getName() ;

        UserDTO loggedInUser = userService.getUserByEmail(username) ;

        //Validating user
        utility.validateUser(token , username , authorities);

        UserDTO user = userService.getUserById(loggedInUser.getId()) ;
        return ResponseEntity.ok(user) ;
    }

    @PutMapping()
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO ,
                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String token){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String username = authentication.getName() ;

        UserDTO loggedInUser = userService.getUserByEmail(username) ;

        //Validating user
        utility.validateUser(token , username , authorities);

        UserDTO updatedUser = userService.updateUser(userDTO , loggedInUser.getId()) ;
        return ResponseEntity.ok(updatedUser) ;
    }

    @DeleteMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUser(@RequestHeader("userId") Integer userId){
        userService.deleteUser(userId); ;
        return ResponseEntity.ok("Deleted Successfully") ;
    }

    @GetMapping("/shift")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_STAFF')")
    public ResponseEntity<List<UserDTO>> getAllUserByShift(@RequestParam String shift){
        List<UserDTO> userDTOS = userService.getAllUserByShift(shift) ;
        return ResponseEntity.ok(userDTOS) ;
    }

//
//    @PostMapping("/enroll/user/{userId}/batch/{batchId}")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_STAFF')")
//    public ResponseEntity<UserDTO> enrollToBatch(@PathVariable Integer userId ,
//                                                 @PathVariable Integer batchId){
//
//        UserDTO updatedUser = userService.enrolledToBatch(userId , batchId) ;
//        return ResponseEntity.ok(updatedUser) ;
//    }
}


