package com.project.attendance.Service;

import com.project.attendance.Model.User;
import com.project.attendance.Payload.DTO.UserDTO;
import com.project.attendance.Payload.Response.ApiResponse;

import java.util.List;

public interface StaffService {
    public UserDTO createStaff(UserDTO userDTO) ;
    ApiResponse assignTraineeToTrainer(User trainer, User trainee);
    List<User> getTrainees(User trainer);
    List<User> getTrainers(User trainee);
}
