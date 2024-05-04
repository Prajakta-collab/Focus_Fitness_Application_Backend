package com.project.attendance.Controller;

import com.project.attendance.Model.Batch;
import com.project.attendance.Payload.UserDTO;
import com.project.attendance.ServiceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserServiceImpl userService ;


    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getAllUser(){
        List<UserDTO> allUsers = userService.getAllUser() ;
        return ResponseEntity.ok(allUsers) ;
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer userId){
        UserDTO user = userService.getUserById(userId) ;
        return ResponseEntity.ok(user) ;
    }

    @PutMapping("{userId}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO ,
                                               @PathVariable Integer userId){
        UserDTO updatedUser = userService.updateUser(userDTO , userId) ;
        return ResponseEntity.ok(updatedUser) ;
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId){
        userService.deleteUser(userId); ;
        return ResponseEntity.ok("Deleted Successfully") ;
    }

    @GetMapping("/shift")
    public ResponseEntity<List<UserDTO>> getAllUserByShift(@RequestParam String shift){
        List<UserDTO> userDTOS = userService.getAllUserByShift(shift) ;
        return ResponseEntity.ok(userDTOS) ;
    }

    @PostMapping("/enroll/user/{userId}/batch/{batchId}")
    public ResponseEntity<UserDTO> enrollToBatch(@PathVariable Integer userId ,
                                                 @PathVariable Integer batchId){

        UserDTO updatedUser = userService.enrolledToBatch(userId , batchId) ;
        return ResponseEntity.ok(updatedUser) ;
    }
}


