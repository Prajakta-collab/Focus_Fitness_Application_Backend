package com.project.attendance.Service;

import com.project.attendance.Model.User;
import com.project.attendance.Payload.DTO.UserDTO;

import java.util.List;

public interface UserService {
    public UserDTO createUser(UserDTO userDTO , String otp) ;
    public List<UserDTO> getAllUser() ;
    public User getUserByEmail(String email) ;
    public User getUserById(Integer userId) ;
    public UserDTO updateUser(UserDTO userDTO , Integer userId) ;
    public void deleteUser(Integer userId) ;
    public List<UserDTO> getAllUserByShift(String shift) ;
    public UserDTO enrolledToBatch(Integer userId ,Integer batchId) ;
}
