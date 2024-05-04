package com.project.attendance.Service;

import com.project.attendance.Payload.UserDTO;

import java.util.List;

public interface UserService {
    public UserDTO createUser(UserDTO userDTO) ;
    public List<UserDTO> getAllUser() ;
    public UserDTO getUserById(Integer userId) ;
    public UserDTO updateUser(UserDTO userDTO , Integer userId) ;
    public void deleteUser(Integer userId) ;
    public List<UserDTO> getAllUserByShift(String shift) ;
    public UserDTO enrolledToBatch(Integer userId ,Integer batchId) ;
}
