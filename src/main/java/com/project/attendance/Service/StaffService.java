package com.project.attendance.Service;

import com.project.attendance.Model.User;
import com.project.attendance.Payload.DTO.UserDTO;
import com.project.attendance.Payload.Response.ApiResponse;
import com.project.attendance.Payload.Response.PTSessionResponse;

import java.util.List;

public interface StaffService {
    public UserDTO createStaff(UserDTO userDTO) ;
    ApiResponse assignTraineeToTrainer(User trainer, User trainee);
    List<User> getTrainees(User trainer);
    ApiResponse addPTSession(Integer trainerId, Integer traineeId, String exercise, String timeIn, String timeOut, String date);
    List<PTSessionResponse> getPTSessions(Integer trainerId);
    List<PTSessionResponse> getPTSessionsForTrainee(Integer traineeId);
}
