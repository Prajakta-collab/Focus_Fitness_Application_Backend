package com.project.attendance.ServiceImpl;

import com.project.attendance.Config.AppConstants;
import com.project.attendance.Exception.InternalServerException;
import com.project.attendance.Model.Role;
import com.project.attendance.Model.User;
import com.project.attendance.Payload.DTO.UserDTO;
import com.project.attendance.Payload.Response.ApiResponse;
import com.project.attendance.Repository.RoleRepository;
import com.project.attendance.Repository.UserRepository;
import com.project.attendance.Service.StaffService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    @Override
    public ApiResponse assignTraineeToTrainer(User trainer, User trainee) {
        if (trainer.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_STAFF")) &&
                trainee.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {

            System.out.println("in service F if");
            if(trainer.getTrainees().contains(trainee)){
                System.out.println("in service S if");
                return ApiResponse.builder()
                        .message("Trainee is already getting PT")
                        .success(Boolean.FALSE)
                        .className(this.getClass().toString())
                        .build() ;
            }

            trainer.getTrainees().add(trainee);

            if(trainee.getTrainers().contains(trainer)){
                return ApiResponse.builder()
                        .message("Trainee is already getting PT from This trainer")
                        .success(Boolean.FALSE)
                        .className(this.getClass().toString())
                        .build() ;
            }

            trainee.getTrainers().add(trainer);

            userRepository.save(trainer);
            userRepository.save(trainee);

            return ApiResponse.builder()
                    .message("PT Assigned Successfully")
                    .success(Boolean.TRUE)
                    .className(this.getClass().toString())
                    .build() ;
        } else {
            throw new IllegalArgumentException("Trainer must have ROLE_STAFF and trainee must have ROLE_USER");
        }
    }

    @Override
    public List<User> getTrainees(User trainer) {
        return trainer.getTrainees();
    }

    @Override
    public List<User> getTrainers(User trainee) {
        return trainee.getTrainers();
    }
}
