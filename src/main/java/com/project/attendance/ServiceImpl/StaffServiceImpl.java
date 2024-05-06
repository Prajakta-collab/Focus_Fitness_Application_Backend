package com.project.attendance.ServiceImpl;

import com.project.attendance.Config.AppConstants;
import com.project.attendance.Model.Role;
import com.project.attendance.Model.User;
import com.project.attendance.Payload.UserDTO;
import com.project.attendance.Repository.BatchRepository;
import com.project.attendance.Repository.RoleRepository;
import com.project.attendance.Repository.UserRepository;
import com.project.attendance.Service.StaffService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    UserRepository userRepository ;

    @Autowired
    PasswordEncoder passwordEncoder ;

    @Autowired
    RoleRepository roleRepository ;

    @Autowired
    ModelMapper modelMapper ;

    @Autowired
    UserServiceImpl userService ;

    @Override
    public UserDTO createStaff(UserDTO userDTO) {
        User user = modelMapper.map(userDTO , User.class) ;

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setJoining_LocalDate(LocalDate.now());
        user.setEnd_LocalDate(user.getJoining_LocalDate().plusMonths(user.getDuration()));

        /*roles*/
        Role role = roleRepository.findById(AppConstants.STAFF_USER).get();
        user.getRoles().add(role);

        User createdUser = userRepository.save(user) ;

        /* Set shift */
        Integer batchId = user.getShift() == "Morning" ? 1 : 2 ;
        userService.enrolledToBatch(createdUser.getId() , batchId) ;

        return modelMapper.map(createdUser , UserDTO.class) ;
    }

//    public void personalTrainingEnrollment(Integer staffID , Integer userID)
}
